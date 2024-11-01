package annotation.sendNotificationOnSuccess.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


@Aspect
public class AopNotificationsIntegration {
    private static final Logger logger = Logger.getLogger("AopNotificationsIntegration");

    @Pointcut("execution(* *(..))")
    public void anyMethodExecution(){
    }
    @Pointcut("@annotation(SendNotificationOnSuccess) && anyMethodExecution()")
    public void pointCut(){
    }

    @AfterReturning(value = "pointCut()",returning = "output")
    public void handleSendNotificationOnSuccess(JoinPoint joinPoint, Object output) throws IllegalAccessException {
        System.out.println("=======================================");
        var signature = (MethodSignature) joinPoint.getSignature();
        var methodName = signature.getMethod().getName();
        // check action result
        System.out.println(">>> advised on method name = "+ signature.getMethod().getName());
        if(isActionNotCompletedSuccessfully(output)){
            System.out.println(">>> "+methodName+" failed -> exit");
            return;
        }
        System.out.println(">>> "+methodName+" succeeded -> extract event data");
        //Extract annotation parameters

        var annotation  = signature.getMethod().getAnnotation(SendNotificationOnSuccess.class);

        //extract all needed information
        Optional<?> entityId = findEntityId(joinPoint,output,annotation);
        Optional<String> actionDoneBy = getDoneBy();
        var message = annotation.message();
        handleEvent(message, entityId.orElse(null), actionDoneBy.orElse("UNKNOWN"));
    }

    private void handleEvent(String message, Object entityId, String actionDoneBy){
        /* write your code to handle the event as needed here
        * ex: produce event to be consumed and processed by
        * Push notification service, email service, or SMS service
         */
       System.out.println(" $$$$ event data: message: "+message+", entityId : "+entityId+", doneBy:"+actionDoneBy);
    }

    /**
     get user who did the action
     ex: you can get user ID from security context
     */
    private Optional<String> getDoneBy(){

        return Optional.of("test");
    }

    /**
     * validate if method completed successfully or not
     * @param output, the value returned from method after execution
     * @return
     */
    private boolean isActionNotCompletedSuccessfully(Object output){
        // write your code to validate the method return value
        //ex: using integer value to determine if method completed successfully or not
        if(output instanceof Integer returnValue){
            return returnValue < 0; // 0 = success
        }else {
            return true;
        }
    }

    /**
     * find the specified entityId
     * @param joinPoint
     * @param output
     * @param annotation
     * @return
     */
    private Optional<?> findEntityId(JoinPoint joinPoint,Object output ,SendNotificationOnSuccess annotation){
        SearchIn searchIn = annotation.searchIn();
        String entityIdFieldPath = annotation.entityIdFiledPath();
        return switch (searchIn) {
            case params -> findEntityIdInMethodArguments(joinPoint, entityIdFieldPath);
            case returnVal -> findEntityIdInMethodReturnValue(output, entityIdFieldPath);
            default -> Optional.empty();
        };
    }

    private Optional<?> findEntityIdInMethodArguments(JoinPoint joinPoint, String entityIdFieldPath){
        Object entityId = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        var path = splitPath(entityIdFieldPath);
        if(path.length < 1) return Optional.empty();

        int depth = 0;
        var index = findMethodArgumentIndex(methodSignature, path[0]);
        if(index.isEmpty()) return Optional.empty(); // No argument found

        depth++;
        return followPath(joinPoint.getArgs()[index.getAsInt()], path, depth);
    }

    private Optional<?> findEntityIdInMethodReturnValue(Object returnVal, String entityIdFieldPath){
        if(entityIdFieldPath.isEmpty()) return Optional.of(returnVal);
        return followPath(returnVal,splitPath(entityIdFieldPath), 0);
    }

    private Optional<?> followPath(Object objectToSearchIn,String[] path,int depth ){
        Object ob = objectToSearchIn;
        Field field = null;
        while(depth < path.length){
            try {
                field = ob.getClass().getDeclaredField(path[depth]);
                field.setAccessible(true);
                ob = field.get(ob);
                depth++;
            } catch (Exception e) {
                logger.log(Level.SEVERE,"[Exception] AopNotificationsIntegration => followPath =>" + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.of(ob);
    }

    private OptionalInt findMethodArgumentIndex(MethodSignature methodSignature, String name){
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        return IntStream.range(0, parameters.length)
                .filter(i -> name.equals(parameters[i].getName())).findFirst();
    }

    private String[] splitPath(String path){
        return path.split(Pattern.quote("."));
    }
}
