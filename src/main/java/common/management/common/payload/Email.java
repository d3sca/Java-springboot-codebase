package common.management.common.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class Email {
 String subject;
 String body;
 String from;
 String to;
 Map<String, Object> props;
 boolean isHtml=false;
 Attachment attachment;

 public Attachment getAttachment() {
  return attachment;
 }

 public void setAttachment(Attachment attachment) {
  this.attachment = attachment;
 }

 public String getSubject() {
  return subject;
 }

 public void setSubject(String subject) {
  this.subject = subject;
 }

 public String getBody() {
  return body;
 }

 public void setBody(String body) {
  this.body = body;
 }

 public String getFrom() {
  return from;
 }

 public void setFrom(String from) {
  this.from = from;
 }

 public String getTo() {
  return to;
 }

 public void setTo(String to) {
  this.to = to;
 }

 public Map<String, Object> getProps() {
  return props;
 }

 public void setProps(Map<String, Object> props) {
  this.props = props;
 }

 public boolean isHtml() {
  return isHtml;
 }

 public void setHtml(boolean html) {
  isHtml = html;
 }
}
