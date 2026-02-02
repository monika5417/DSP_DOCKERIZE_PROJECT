//package com.mpcz.deposit_scheme.backend.api.domain;
//
//import java.io.IOException;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Testing {
//	
//	
//	 public Response m1() throws IOException {
//	
//	OkHttpClient client = new OkHttpClient().newBuilder()
//			  .build();
//			MediaType mediaType = MediaType.parse("application/jose");
//			RequestBody body = RequestBody.create(mediaType, "eyJhbGciOiJIUzI1NiIsImNsaWVudGlkIjoidWF0bXBta3Z2Iiwia2lkIjoiSE1BQyIsInR5cCI6IkpXVCJ9.eyJtZXJjaWQiOiJVQVRNUE1LVlYiLCJvcmRlcmlkIjoiVFNTR0Y0MzIxNEYxNjgyNTc3NjM4NTY0Nzg3IiwiYW1vdW50IjoiMTIwMC4wIiwib3JkZXJfZGF0ZSI6IjIwMjMtMDQtMjdUMTI6MTA6MzgrMDU6MzAiLCJjdXJyZW5jeSI6IjM1NiIsInJ1IjoiaHR0cHM6Ly9tcHV2bmxyZWNydWl0bWVudHVhdC5tcGN6LmluOjg4ODgvcGF5bWVudC9yZXNwb25zZS8iLCJhZGRpdGlvbmFsX2luZm8iOnsiYWRkaXRpb25hbF9pbmZvMSI6IjMyNzY0NzUyMyIsImFkZGl0aW9uYWxfaW5mbzIiOiJEZWVwYWsiLCJhZGRpdGlvbmFsX2luZm8zIjoiU29uaSIsImFkZGl0aW9uYWxfaW5mbzQiOiJEZWVwYWtAZ21haWwuY29tIiwiYWRkaXRpb25hbF9pbmZvNSI6IjgwMDMwMTc5MjMifSwiaXRlbWNvZGUiOiJESVJFQ1QiLCJpbnZvaWNlIjp7Imludm9pY2VfbnVtYmVyIjoiVFNTR0Y0MzIxNEYxNjgyNTc3NjM4NTY0Nzg3IiwiaW52b2ljZV9kaXNwbGF5X251bWJlciI6IlRTU0dGNDMyMTRGMTY4MjU3NzYzODU2NDc4NyIsImN1c3RvbWVyX25hbWUiOiJEZWVwYWsiLCJpbnZvaWNlX2RhdGUiOiIyMDIzLTA0LTI3VDEyOjEwOjM4KzA1OjMwIiwiZ3N0X2RldGFpbHMiOnsiY2dzdCI6IjAuMDAiLCJzZ3N0IjoiMC4wMCIsImlnc3QiOiIwLjAwIiwiZ3N0IjoiMC4wMCIsImNlc3MiOiIwLjAwIiwiZ3N0aW5jZW50aXZlIjoiMC4wMCIsImdzdHBjdCI6IjAuMDAiLCJnc3RpbiI6IiJ9fSwiZGV2aWNlIjp7ImluaXRfY2hhbm5lbCI6ImludGVybmV0IiwiaXAiOiIxMjUuMTYuMzMuMTQ0IiwidXNlcl9hZ2VudCI6Ik1vemlsbGEvNS4wIChXaW5kb3dzIE5UIDEwLjA7IFdPVzY0OyBydjo1MS4wKUdlY2tvLzIwMTAwMTAxIEZpcmVmb3gvNTEuMCIsImFjY2VwdF9oZWFkZXIiOiJ0ZXh0L2h0bWwifX0.IIRkjXs4hixZlkrDHxF8H7-36SyUqht9cl_T3wz6OWY");
//			Request request = new Request.Builder()
//			  .url("https://pguat.billdesk.io/payments/ve1_2/orders/create")
//			  .method("POST", body)
//			  .addHeader("content-type", "application/jose")
//			  .addHeader("bd-timestamp", "1682577822")
//			  .addHeader("accept", "application/json")
//			  .addHeader("bd-traceid", "168257763856478444")
//			  .build();
//			 Response execute = client.newCall(request).execute();
//System.err.println(execute);
//			 return execute;
//}
//}
