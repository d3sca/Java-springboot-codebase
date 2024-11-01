package annotation.sendNotificationOnSuccess;

import annotation.sendNotificationOnSuccess.aspect.SearchIn;
import annotation.sendNotificationOnSuccess.aspect.SendNotificationOnSuccess;

public class SendNotificationOnSuccessExample {
    public static void main(String[] args) {
        SendNotificationOnSuccessExample example = new SendNotificationOnSuccessExample();
        example.action1(); // will trigger notification

        example.action2(); // no, the method return failure status

        example.action3(); //no

        example.updateBooksById(15L); // will trigger notification

        example.updateUserById(new Request("123456","user1")); //will trigger notification

        example.createNewBook(new Request("123456","book1")); //will trigger notification
    }

    //action without entityId

    /**
     * action without entityId, entityId here represents the ID of entity effected by this action.
     */
    @SendNotificationOnSuccess(message = "action 1 called")
    private Integer action1(){
        return 0; //success
    }

    //failed action
    @SendNotificationOnSuccess(message = "action 2 called")
    private Integer action2(){
        return -1; //failed
    }

    /**
    * no annotation on action3, so it must not be
     * */
    private Integer action3(){
        return 0;
    }

    /**
     * read entityID from method parameters.
     * write the path to the field separated by a dot.
     * ex: if method parameter named "bookId" then path = "bookId"
     */
    @SendNotificationOnSuccess(message = "Book has been updated", searchIn = SearchIn.params, entityIdFiledPath = "bookId")
    private Integer updateBooksById(Long bookId){
        return 0;
    }

    /**
     * read entityID from method parameters [nested]
     * write the path to the field separated by a dot
     * ex: class Request{
     *     String userId,
     *     String name
     * }
     * path = request.userId
     */
    @SendNotificationOnSuccess(message = "User has been updated", searchIn = SearchIn.params, entityIdFiledPath = "request.userId")
    private Integer updateUserById(Request request){
        return 0;
    }

    /**
    * read entityId from method return value
     * */
    @SendNotificationOnSuccess(message = "New Book has been added", searchIn = SearchIn.returnVal)
    private Integer createNewBook(Request request){
        //create book and return the created book ID
        return 123;
    }

}
