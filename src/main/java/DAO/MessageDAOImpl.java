package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import Model.Message;
import Util.ConnectionUtil;
import Util.DatabaseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDAOImpl implements MessageDAO{
    private static final Logger logger = LoggerFactory.getLogger(MessageDAOImpl.class);

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
                logger.info("Message created with id: {} by user: {}", generatedMessageId, msg.getPosted_by());
                return msg;
            }
        } catch (SQLException e) {
            logger.error("Error creating message: {}", e.getMessage());
        } finally {
            DatabaseUtil.closeResource(rs);
            DatabaseUtil.closeResource(pstmt);
            DatabaseUtil.closeResource(connection);
        }
        return null;
    }

    @Override
    public Message getMessageById(int msgId) {
        String sql = "SELECT * FROM message WHERE message_id = ?;";

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, msgId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                Message message = new Message();
                message.setMessage_id(msgId);
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                logger.info("Retrieved message with ID: {}", msgId);
                return message;
            }

        } catch (SQLException e) {
            logger.error("Error retrieving message with ID {}: {}", msgId, e.getMessage());
        } finally {
            DatabaseUtil.closeResource(rs);
            DatabaseUtil.closeResource(pstmt);
            DatabaseUtil.closeResource(connection);
        }

        return null;
    }

    @Override
    public List<Message> getAllMessages() {
        String sql = "SELECT * FROM message;";
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Message> messages = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            logger.info("Retrieved {} messages", messages.size());
        } catch (SQLException e) {
            logger.error("Error retrieving all messages: {}", e.getMessage());
        } finally {
            DatabaseUtil.closeResource(rs);
            DatabaseUtil.closeResource(stmt);
            DatabaseUtil.closeResource(connection);
        }
        return messages;
    }

    @Override
    public List<Message> getAllMessagesByAccountId(int accountId) {
        String sql = "SELECT * FROM message WHERE posted_by = ?;";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Message> messages = new ArrayList<>();

        try {
            connection = ConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, accountId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Message message = new Message();
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
            logger.info("Retrieved {} messages for account with ID: {}", messages.size(), accountId);
        } catch (SQLException e) {
            logger.error("Error retrieving all messages for account with ID {}: {}", accountId, e.getMessage());
        } finally {
            DatabaseUtil.closeResource(rs);
            DatabaseUtil.closeResource(pstmt);
            DatabaseUtil.closeResource(connection);
        }
        return messages;
    }

    @Override
    public Message updateMessageText(int msgId, String newText) {
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newText);
            pstmt.setInt(2, msgId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Updated message text for message with ID: {}", msgId);
                return getMessageById(msgId);
            }

        } catch (SQLException e) {
            logger.error("Error updating message text for message with ID {}: {}", msgId, e.getMessage());
        } finally {
            DatabaseUtil.closeResource(pstmt);
            DatabaseUtil.closeResource(connection);
        }

        return null;
    }

    @Override
    public Message deleteMessage(int msgId) {
        String sql = "DELETE FROM message WHERE message_id = ?;";
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, msgId);

            Message deletedMessage = getMessageById(msgId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Deleted message with ID: {}", msgId);
                return deletedMessage;
            }
        } catch (SQLException e) {
            logger.error("Error deleting message with ID {}: {}", msgId, e.getMessage());
        } finally {
            DatabaseUtil.closeResource(pstmt);
            DatabaseUtil.closeResource(connection);
        }

        return null;
    }

}