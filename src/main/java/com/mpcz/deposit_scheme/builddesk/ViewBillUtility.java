//package com.mpcz.deposit_scheme.builddesk;
//
//import java.security.MessageDigest;
//import java.util.StringJoiner;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.mpcz.deposit_scheme.backend.api.builddesk.PaymentProperties;
//import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentRequest1;
//
//
//
//@Component
//public class ViewBillUtility {
//
//	@Autowired(required=false)
//	PaymentProperties paymentProperties;
//
////	@Value("{payment.merchantId}")
////	private String billDeskMerchantId;
//
//	public String getBillDeskCheckSum(BillDeskPaymentRequest1 request) {
//
//		final StringJoiner joiner = new StringJoiner("|");
//		joiner.add(request.getMerchantId()).add(request.getCustomerId()).add(request.getFiller1())
//				.add(request.getTxnAmount().toString()).add(request.getBankid()).add(request.getFiller2())
//				.add(request.getFiller3()).add(request.getCurrencyType()).add(request.getItemCode())
//				.add(request.getTypeField1()).add(request.getSecurityId()).add(request.getFiller4())
////				.add(request.getFiller5()).add(request.getTypeField2()).add(request.getAdditionalInfo1())
////				.add(request.getAdditionalInfo2()).add(request.getAdditionalInfo3()).add(request.getAdditionalInfo4())
//				.add(request.getAdditionalInfo5()).add(request.getAdditionalInfo6()).add(request.getAdditionalInfo7())
//				.add(request.getRu());
//
//		System.out.println(joiner+"SAAAAAAAAAAAAAAAAAAAA");
//		String checksum = null;
//		try {
//			//String ChecksumKey = paymentProperties.getBillDeskChecksumKey();
//			String ChecksumKey ="MPMKCZPAY";
//
//			checksum = HmacSHA256(joiner.toString(), ChecksumKey);
//			System.out.println("BillDesk Checksum = " + checksum);
//			joiner.add(checksum);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("BillDesk Message String = " + joiner.toString());
//		return joiner.toString();
//
//	}
//
//	public String HmacSHA256(String message, String secret) {
//		MessageDigest md = null;
//		try {
//
//			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//			sha256_HMAC.init(secret_key);
//
//			byte raw[] = sha256_HMAC.doFinal(message.getBytes());
//
//			StringBuffer ls_sb = new StringBuffer();
//			for (int i = 0; i < raw.length; i++)
//				ls_sb.append(char2hex(raw[i]));
//			return ls_sb.toString(); // step 6
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	private String char2hex(byte x)
//
//	{
//		char arr[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
//
//		char c[] = { arr[(x & 0xF0) >> 4], arr[x & 0x0F] };
//		return (new String(c));
//	}
//	/*
//	 * public static void main(String a[]) { String checksum = new
//	 * ViewBillUtility().HmacSHA256(
//	 * "0122|MPMKBHOPAL|2020122466556469|20201226180510",
//	 * "Nlnu1yPTZHAqJbp7YP7kVjGE6KZDp1dp");
//	 * System.out.println("checksumm>>>"+checksum); }
//	 */
//
//	public static void mian(String args[]) {
//		BillDeskPaymentRequest1 vid=new BillDeskPaymentRequest1(); 
//		vid.setMerchantId("mpmkvvcl");
//		vid.setCustomerId("1234567");
//		vid.setFiller1("sdgsdf");
//		ViewBillUtility v =new ViewBillUtility();
//		v.getBillDeskCheckSum(vid);
//		
//	}
//
//
//}