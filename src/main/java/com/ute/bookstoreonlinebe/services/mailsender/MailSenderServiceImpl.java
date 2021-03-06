package com.ute.bookstoreonlinebe.services.mailsender;

import com.ute.bookstoreonlinebe.entities.Order;
import com.ute.bookstoreonlinebe.entities.User;
import com.ute.bookstoreonlinebe.entities.embedded.EmbeddedBookInOrder;
import com.ute.bookstoreonlinebe.models.EmailDetails;
import com.ute.bookstoreonlinebe.models.MyConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private JavaMailSender javaMailSender;

    private final Configuration configuration;

    private MyConstants myConstants = new MyConstants();

    public MailSenderServiceImpl(JavaMailSender javaMailSender, Configuration configuration) {
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Override
    public void sendMailSignup(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("Chao mung ban den voi FESHBOOK");
            helper.setTo(user.getEmail());
            String emailContent = getEmailSignupContent(user);
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException me){}
        catch (TemplateException te){}
        catch (IOException ie){}
    }

    @Override
    public void sendMailNewOrder(User user, Order order) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("FESHBOOK th??ng b??o ????n ?????t h??ng");
            helper.setTo(user.getEmail());
            String emailContent = getEmailOrderContent(user, order);
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException me){}
        catch (TemplateException te){}
        catch (IOException ie){}
    }

    @Override
    public void sendMailCallOffOrder(User user, Order order) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("FESHBOOK th??ng b??o h???y ????n h??ng");
            helper.setTo(user.getEmail());
            String emailContent = getEmailCallOfOrderContent(user, order);
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException me){}
        catch (TemplateException te){}
        catch (IOException ie){}
    }

    @Override
    public void sendMailChangePassword(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject("FESHBOOK th??ng b??o thay ?????i m???t kh???u");
            helper.setTo(user.getEmail());
            String emailContent = getEmailChangePasswordContent(user);
            helper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);
        }catch (MessagingException me){}
        catch (TemplateException te){}
        catch (IOException ie){}
    }

    @Override
    public void sendMailForgotPassword(User user) {

    }

    public void sendEmail(User user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome To SpringHow.com");
        helper.setTo(user.getEmail());
        String emailContent = getEmailContent(user);
        helper.setText(emailContent, true);
        javaMailSender.send(mimeMessage);
    }
    String getEmailContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        configuration.getTemplate("email.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    String getEmailSignupContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        configuration.getTemplate("email-signup.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    String getEmailOrderContent(User user, Order order) throws IOException, TemplateException {
        List<EmbeddedBookInOrder> books = order.getBooksInOrder();

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("order", order);
        model.put("orderDate", order.getOrderDate().toString());
        model.put("books", books);
        model.put("sumTotal", order.getSubtotal().getPrice());
        configuration.getTemplate("email-order.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    String getEmailCallOfOrderContent(User user, Order order) throws IOException, TemplateException {

        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        model.put("order", order);
        model.put("time", LocalDateTime.now().with(LocalDateTime.MAX).toString());
        configuration.getTemplate("email-call-off-order.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    String getEmailChangePasswordContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        configuration.getTemplate("email-change-password.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }
    String getEmailForgotPasswordContent(User user) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        configuration.getTemplate("email-forgot-password.ftl").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }


    // To send a simple email
//    public void sendSimpleMail(EmailDetails details) throws MessagingException, IOException, TemplateException {
//
//        // Try block to check for exceptions
//        try {
//
//            // Creating a simple mail message
//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//
//            // Setting up necessary details
//            mailMessage.setFrom(myConstants.MY_EMAIL);
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());
//
//            // Sending the mail
//            javaMailSender.send(mailMessage);
//            return "Mail Sent Successfully...";
//        }
//
//        // Catch block to handle the exceptions
//        catch (Exception e) {
//            return "Error while Sending Mail";
//        }
//    }
//
//    @Override
//    public void sendMailSignup(User user){
//        String subject = "Welcome to FESHBOOK!";
//        String msgBody = String.format("Ch??o m???ng %s ???? ?????n v???i FESHBOOK!", user.getFullname());
//
//        return sendSimpleMail(new EmailDetails(user.getEmail(), msgBody, subject, null));
//    }
//
//    @Override
//    public void sendMailNewOrder(User user, Order order) {
//        String subject = "Welcome FESHBOOk!";
//        String msgBodyXC = String.format("Xin ch??o:  %s", user.getFullname());
//        String msgBodyCT = String.format("\nB???n ???? ?????t h??ng th??nh c??ng, v???i m?? ????n h??ng l??: %s ", order.getId());
//        String msgOrderTime = String.format("\nV??o l??c: %s", order.getOrderDate());
//        String msgTime = "\nTh??i gian giao h??ng d??? ki???n t??? 3 ?????n 5 ng??y, kh??ng t??nh th??? 7 v?? ch??? nh???t.";
//        String msgBody = msgBodyXC + msgBodyCT + msgOrderTime + msgTime;
//        return sendSimpleMail(new EmailDetails(user.getEmail(), msgBody, subject, null));
//    }
//
//    @Override
//    public void sendMailCallOffOrder(User user, String orderID){
//        String subject = "Welcome FESHBOOk!";
//        String msgBodyXC = String.format("Xin ch??o:  %s", user.getFullname());
//        String msgBodyCT = String.format("\nB???n ???? h??y ????n h??ng c?? m??: %s ", orderID);
//        String msgTime = String.format(" v??o l??c: %s", new Date());
//        String msgBody = msgBodyXC + msgBodyCT + msgTime;
//        return sendSimpleMail(new EmailDetails(user.getEmail(), msgBody, subject, null));
//    }
//
//    @Override
//    public void sendMailChangePassword(User user){
//        String subject = "Welcome FESHBOOk!";
//        String msgBodyXC = String.format("Xin ch??o:  %s", user.getFullname());
//        String msgBodyCT = String.format("\nB???n ???? th???c hi???n thay ?????i m???t kh???u th??nh c??ng v??o l??c %s", new Date());
//        String msgBodyWRN = "\nN???u b???n ???? kh??ng ph???i b???n, hay li???n h??? v???i ch??ng t??i ngay theo email sau: zerodev47@gmail.com";
//        String msgBody = msgBodyXC + msgBodyCT + msgBodyWRN;
//        return sendSimpleMail(new EmailDetails(user.getEmail(), msgBody, subject, null));
//    }
//
//    @Override
//    public void sendMailForgotPassword(User user) {
//        return null;
//    }
}
