package com.wipro.bank.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.wipro.bank.bean.TransferBean;
import com.wipro.bank.util.DBUtil;

public class BankDAO {
    public boolean validateAccount(String accountNumber) {
        String sql = "SELECT 1 FROM account_tbl WHERE account_number = ?";
        try (Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public float findBalance(String accountNumber) {
        String sql = "SELECT balance FROM account_tbl WHERE account_number = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean updateBalance(String accountNumber, float newBalance) {
        String sql = "UPDATE account_tbl SET balance = ? WHERE account_number = ?";
        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setFloat(1, newBalance);
            ps.setString(2, accountNumber);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transferMoney(TransferBean transferBean) {
    	String sql =
    			"INSERT INTO transfer_tbl " +
    			"(account_number, beneficiary_account_number, transaction_date, transaction_amount) " +
    			"VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, transferBean.getFromAccountNumber());
            ps.setString(2, transferBean.getToAccountNumber());
            ps.setDate(3, new Date(transferBean.getDateOfTransaction().getTime()));
            ps.setFloat(4, transferBean.getAmount());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
