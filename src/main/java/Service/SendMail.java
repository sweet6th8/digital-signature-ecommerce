package Service;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    // Cấu hình thông tin SMTP
    private final String host = "smtp.gmail.com"; // SMTP server của Gmail
    private final String port = "587"; // Port mặc định của Gmail
    private final String senderEmail = "22130015@st.hcmuaf.edu.vn"; // Email gửi
    private final String senderPassword = "sqtw uubu kqtc lcje"; // Mật khẩu ứng dụng (app password)
    private  Properties properties ;
    private Message message;
    public SendMail() throws MessagingException {
        // Config stmp
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Hoặc máy chủ SMTP bạn sử dụng
        // Create session with email
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        message = new MimeMessage(session);


    }
    public Message activeAcount (String email, int id) throws MessagingException {
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Nhấn vào link để kích hoạt tài khoản của bạn : ");
        message.setText("http://localhost:8080/FashionWebProject/Active?id="+id);
        return message;
    }
    public Message sendMail (String email, String otp) throws MessagingException {
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("[Shopping Web] Mã OTP của bạn");
        message.setText("Chúng tôi gửi bạn mã OTP là: " + otp);
        return message;
    }
}