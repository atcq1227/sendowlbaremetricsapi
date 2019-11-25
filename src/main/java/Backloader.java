import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import handlers.lambda.BackloadHandler;
import util.EmailUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Backloader {
    public static void main(String[] args) throws Exception {
        //new EmailUtil().sendEmail();
        //new BackloadHandler().deleteAllSubscriptions();
        //new BackloadHandler().handle();
        //problemFinder();
    }

    private static void problemFinder() throws FilloException, ParseException {
        Fillo fillo = new Fillo();

        Connection connection = fillo.getConnection("/Users/matthewshaw/Downloads/orders_report.xlsx");

        String query = "Select * from Worksheet";

        Recordset rs = connection.executeQuery(query);

        while(rs.next()) {
//            if(rs.getField("Buyer Name").equals("Anton Lucas")) {
//                System.out.println("found");
//                break;
//            }

            if(rs.getField("Refunded").equals("true")) {
                System.out.println("found");
                break;
            }
        }

//        Date createdAt = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Order date/time"));
//        Date cancelledAt = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss").parse(rs.getField("Subscription Cancelled At"));
//
//        System.out.println("Created at: " + String.valueOf(createdAt));
//        System.out.println("Cancelled at: " + String.valueOf(cancelledAt));
//        System.out.println(createdAt.compareTo(cancelledAt));
    }
}
