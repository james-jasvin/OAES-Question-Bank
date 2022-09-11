//package com.iiitb.oaes.Service;
//
//import com.academia.payment.bean.Bill;
//import com.academia.payment.bean.Receipt;
//import com.academia.payment.bean.Student;
//import com.academia.payment.dao.impl.BillDAOImpl;
//import com.academia.payment.dao.impl.StudentDAOImpl;
//import com.iiitb.oaes.DAO.ItemDao;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ItemService {
//    ItemDao itemDAO = new BillDAOImpl();
//
//    public List<Bill> getBills(Integer s_id){
//        List<Bill> billList = itemDAO.getBills(s_id);
//
//        // Removing student attribute and receipt attributes from returned list to kill cyclic references
//        for (Bill bill: billList) {
//            bill.setStudent(null);
//            bill.setReceiptList(null);
//        }
//
//        return billList;
//    }
//
//    public Integer payBills(HashMap<Integer, Receipt> paymentDictionary) {
//        Integer successfulPayments = 0;
//
//        for (Map.Entry<Integer, Receipt> keyValuePair: paymentDictionary.entrySet()) {
//            Integer billId = keyValuePair.getKey();
//            Receipt receipt = keyValuePair.getValue();
//
//            successfulPayments += itemDAO.payBills(billId, receipt);
//        }
//
//        return successfulPayments;
//    }
//}