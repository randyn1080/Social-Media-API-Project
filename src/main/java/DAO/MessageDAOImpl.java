package DAO;

import java.sql.*;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAOImpl implements MessageDAO{

    @Override
    public Message createMessage(Message msg) {
        String SQL = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, msg.getPosted_by());
            pstmt.setString(2, msg.getMessage_text());
            pstmt.setLong(3, msg.getTime_posted_epoch());

            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedMessageId = rs.getInt(1);
                msg.setMessage_id(generatedMessageId);
                return msg;
            }
        } catch (SQLException e) {
            //TODO: handle exception
        }
        return null;
    }

    @Override
    public Message getMessageById(int msgId) {
        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        return null;
    }

    @Override
    public List<Message> getAllMessagesByAccountId(int accountId) {
        return null;
    }

    @Override
    public Boolean updateMessage(Message message) {
        return false;
    }

    @Override
    public Boolean deleteMessage(int msgId) {
        return false;
    }
    
}
