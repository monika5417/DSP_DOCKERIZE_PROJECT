package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mpcz.deposit_scheme.backend.api.domain.BillDeskPaymentResponse;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ContractorForBid;
import com.mpcz.deposit_scheme.backend.api.domain.MmkyPayAmount;
import com.mpcz.deposit_scheme.backend.api.repository.BillPaymentResponseeeeeeeRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ContractorForBidRepository;
import com.mpcz.deposit_scheme.backend.api.repository.MmkyPayAmountRespository;
import com.mpcz.deposit_scheme.backend.api.request.SMSRequest;
import com.mpcz.deposit_scheme.backend.api.response.Response;
import com.mpcz.deposit_scheme.backend.api.services.ApplicationStatusService;
import com.mpcz.deposit_scheme.backend.api.services.SMSDirectService;
import com.mpcz.deposit_scheme.backend.api.utility.MessageProperties;

//@RestController
//@RequestMapping("/stream")
public class StreamController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private static BillPaymentResponseeeeeeeRepository billpaymentResponse1;

	@Autowired
	private static ConsumerApplictionDetailRepository consumerApplictionDetailRepository1;

//	public static void main(String args[]) {
//		m124();
////		m();
//	}

//	public static void m() {
//		consumerApplictionDetailRepository1.findAll().stream()
//				.filter(cad -> cad.getApplicationStatus().getApplicationStatusId() == 21L).forEach(cad1 -> {
//					List<BillDeskPaymentResponse> findByConsumerApplicationNo = billpaymentResponse1
//							.findByConsumerApplicationNo(cad1.getConsumerApplicationNo());
//					findByConsumerApplicationNo.stream().filter(bill -> bill.getAuth_status().equals("0300")
//							&& bill.getAdditionalInfo().equals("Demand_Fess")).forEach(bill1 -> {
//								String transactionDate = bill1.getTransactionDate();
//
//								String dateString = transactionDate;
//
//								LocalDateTime dateTime = LocalDateTime.parse(dateString,
//										DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//
//								String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//								System.out.println("Formatted Date: " + formattedDate);
//
//								LocalDateTime modifiedDateTime = dateTime.plusDays(3);
//
//								// Format the modified date
//								String modifiedFormattedDate = modifiedDateTime
//										.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//								cad1.setPaymentDate(modifiedFormattedDate);
//								
////								ConsumerApplicationDetail save = consumerApplictionDetailRepository.save(cad1);
//
//							});
//
//				});
//
////		Map<ConsumerApplicationDetail, List<ConsumerApplicationDetail>> collect = consumerApplictionDetailRepository
////				.findAll().stream().collect(Collectors.groupingBy(Function.identity()));
////		System.out.println(collect);
//	}

	public static void m1() {
//		1) Given a list of integers, separate odd and even numbers?

		List<Integer> l = new ArrayList<>();
		l.add(13);
		l.add(1);
		l.add(23);
		l.add(10);
		l.add(11);
		l.add(313);
		l.add(18);
		l.add(1875);
		l.add(13828390);
		l.add(1282);
		l.add(103);
		l.add(681361);
		l.add(130);
//		l.stream().collect(Collectors.partitioningBy(s -> s % 2 == 0)).entrySet().forEach(s1 -> {
//			s1.getValue().forEach(s2 -> {
//				if (s1.getKey()) {
//
//					System.out.println("this is even no" + s2);
//				} else {
//					System.out.println("this is odd no" + s2);
//
//				}
//			});
//		});

	}

	public static void m2() {

		String name = "charitra kumar prajapti";
		Map<Character, Long> collect = name.chars().mapToObj(s -> (char) s)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(collect);
	}

	public static void m3() {

		String name = "charitra kumar prajapti";

		Map<Character, Long> collect = name.chars().mapToObj(s -> (char) s)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

	}

	public static void m124() {

//				  consumerApplictionDetailRepository1.findAll().stream()
//				.collect(Collectors.groupingBy(cad->cad.getApplicationStatus().getApplicationStatusId(),Collectors.counting())).entrySet().forEach(s->{
//					System.out.println(s.getKey());
//				});

		List<ConsumerApplicationDetail> findAll = consumerApplictionDetailRepository1.findAll();
		System.out.println(findAll);
		Map<Long, Long> collect = findAll.stream().collect(
				Collectors.groupingBy(f1 -> f1.getApplicationStatus().getApplicationStatusId(), Collectors.counting()));
		System.out.println(collect);
	}

	@Autowired
	MmkyPayAmountRespository mmkyPayAmountRespository;

	@Autowired
	BillPaymentResponseeeeeeeRepository billPaymentResponseeeeeeeRepository;

	@Autowired
	ApplicationStatusService applicationStatusService;

	public void deActiveAppAfter30Days() {
		Response<Object> response = new Response<Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateStr = formatter.format(new Date());
		LocalDate currentDate = LocalDate.parse(currentDateStr);
		System.out.println(currentDate);

		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAll();
//		for (MmkyPayAmount M : findAll) {
//
//			LocalDate dbDate = LocalDate.parse(M.getCreated());
//
//			LocalDate currentDate1 = dbDate.plusDays(30);
//			LocalDate date2 = LocalDate.parse(s);
//
//			if (currentDate1.compareTo(date2) < 0) {
//				List<BillDeskPaymentResponse> findByConsumerApplicationNo = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNo(M.getConsumerApplicationNumber());
//				if (findByConsumerApplicationNo.isEmpty()) {
//					M.setActive(false);
//					M.setDeleted(true);
//					System.out.println(M.getConsumerApplicationNumber());
//					ConsumerApplicationDetail findByConsumerApplicationNumber = consumerApplictionDetailRepository
//							.findByConsumerApplicationNumber(M.getConsumerApplicationNumber());
//					if (findByConsumerApplicationNumber != null) {
//						findByConsumerApplicationNumber.setActive(false);
//						findByConsumerApplicationNumber.setDeleted(true);
//						ApplicationStatus applicationStatus = applicationStatusService.findById(
//								ApplicationStatusEnum.APPLICATION_REJECTED_DEMAND_NOTE_PAYMENT_NOT_DONE_WITHIN_30_DAYS
//										.getId());
//						findByConsumerApplicationNumber.setApplicationStatus(applicationStatus);
//						consumerApplictionDetailRepository.save(findByConsumerApplicationNumber);
//					}
//				}
//
//				mmkyPayAmountRespository.save(M);
//			}
//		}

//		findAll.stream().filter(d -> {
//			LocalDate dbDate = LocalDate.parse(d.getCreated());
//			LocalDate extendeDate = dbDate.plusDays(30);
//			return extendeDate.compareTo(currentDate) < 0;
//		}).filter(d -> billPaymentResponseeeeeeeRepository.findByConsumerApplicationNo(d.getConsumerApplicationNumber())
//				.isEmpty()).forEach(m -> {
//					m.setActive(false);
//					m.setDeleted(true);
//
//					ConsumerApplicationDetail consumerApplicationNumber = consumerApplictionDetailRepository
//							.findByConsumerApplicationNumber(m.getConsumerApplicationNumber());
//					consumerApplicationNumber.setActive(false);
//					consumerApplicationNumber.setDeleted(true);
//				consumerApplicationNumber.setApplicationStatus(applicationStatusService.findById(
//							ApplicationStatusEnum.APPLICATION_REJECTED_DEMAND_NOTE_PAYMENT_NOT_DONE_WITHIN_30_DAYS
//									.getId()));
//				consumerApplictionDetailRepository.save(consumerApplicationNumber);
//
//				}
//				mmkyPayAmountRespository.save(m);
//						);

	}

	@Autowired
	MessageProperties messageProperties;

	@Autowired
	SMSDirectService smsDirectService;

//	 @GetMapping("/mkmyMsgAfter25Days")
//		@Scheduled(fixedRate = 86400000)    // 24 Hour
//	public void mkmyMsgAfter25Days() {
//		Response<Object> response = new Response<Object>();
//		LocalDate currentDate = LocalDate.now();
//		System.err.println("currentDate : " + currentDate);
//
//		List<MmkyPayAmount> findAll = mmkyPayAmountRespository.findAllUnsendMsg();
//
//		findAll.stream().forEach(m -> {
//			LocalDate createDateDB = LocalDate.parse(m.getCreated());
//			System.err.println("createDateDB : " + createDateDB);
//			if (createDateDB.compareTo(currentDate) == 0) {
//				List<BillDeskPaymentResponse> paymentDB = billPaymentResponseeeeeeeRepository
//						.findByConsumerApplicationNo(m.getConsumerApplicationNumber());
//				if (paymentDB.isEmpty()) {
//					ConsumerApplicationDetail consumerapplication = consumerApplictionDetailRepository
//							.findByConsumerApplicationNumber(m.getConsumerApplicationNumber());
//
//					final SMSRequest smsRequest = new SMSRequest();
//
//					smsRequest.setText(MessageFormat.format(messageProperties.getMkmyPaymentRemainFiveDay(),
//							new Object[] { m.getConsumerApplicationNumber() }));
//					smsRequest.setMobileNo(consumerapplication.getConsumers().getConsumerLoginId());
//					smsRequest.setTemplateId(messageProperties.getMkmyPaymentRemainTempId());
////											code added by monika on 30-August-2024
//					smsRequest.setHinglish(1L);
////											code end
//
//					try {
//						String sendMessage = smsDirectService.sendMessage(smsRequest);
//						if (!sendMessage.equalsIgnoreCase(null)) {
//							m.setMsgSend("Send");
//							mmkyPayAmountRespository.save(m);
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//
//		});
//
//	}

//	@Override
//	public RefundAmount saveConsumerApplicationCancel(RefundAmount refundAmount)
//			throws ConsumerApplicationDetailException {
//
//		BigDecimal refundableAmount = BigDecimal.ZERO;
//		BigDecimal totalWapaskrneWalaPaisa = BigDecimal.ZERO;
//
//		ConsumerApplicationDetail consumerApplicationDetail = consumerApplictionDetailRepository
//				.findByConsumerApplicationNumber(refundAmount.getConsumerApplicationNo());
//		if (Objects.isNull(consumerApplicationDetail)) {
//			throw new ConsumerApplicationDetailException(
//					new Response(HttpCode.NULL_OBJECT, "Application Not Found In Database"));
//		} else {
//			List<BillDeskPaymentResponse> allPaymentDetails = billPaymentResponseeeeeeeRepository
//					.getAllPaymentDetails(refundAmount.getConsumerApplicationNo());
//
//			for (BillDeskPaymentResponse bill : allPaymentDetails) {
//				ConsumerAppCancellationRefundAmount conAppCancel = new ConsumerAppCancellationRefundAmount();
//				conAppCancel.setApplicationNo(refundAmount.getConsumerApplicationNo());
//				conAppCancel.setMerchantId(bill.getMercid());
//				conAppCancel.setOrderId(bill.getOrderid());
//				conAppCancel.setPaymentType(bill.getAdditionalInfo());
//				conAppCancel.setTxnAmount(new BigDecimal(bill.getAmount()));
//				conAppCancel.setTxnId(bill.getTranId());
//				conAppCancel.setTransactionDate(bill.getTransactionDate());
//				consumerAppCancellationRefundAmountRepository.save(conAppCancel);
//
//			}
//			List<ErpRev> erpRevDB = erpRevRepository.findByConsumerAppNo(refundAmount.getConsumerApplicationNo());
//			if (!erpRevDB.isEmpty()) {
//				for (ErpRev erpRev : erpRevDB) {
//					System.err.println("erp aaaaaa : " + erpRev.getPayAmt());
//
//					ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//							.findByApplicationNoAndTxnAmount(refundAmount.getConsumerApplicationNo(),
//									erpRev.getPayAmt());
//					if (erpRev.getRemCgst() != null) {
//						BigDecimal remCgst = erpRev.getRemCgst();
//						BigDecimal remSgst = erpRev.getRemSgst();
//						BigDecimal totalCgstSgst = remCgst.add(remSgst);
//
//						refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//					} else {
//						refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//					}
//					applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//					consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//
//					totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//				}
//			}
//			ErpEstimateAmountData erpEstimateDB = estimateAmountRepository
//					.findByErpNo(consumerApplicationDetail.getErpWorkFlowNumber());
//
//			ConsumerAppCancellationRefundAmount applicationNoAndTxnAmountDB = null;
//			if (consumerApplicationDetail.getSchemeType().getSchemeTypeId().equals(1L)) {
//				applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//						.findByApplicationNoAndTxnAmount(refundAmount.getConsumerApplicationNo(),
//								erpEstimateDB.getTotalBalanceSupervisionAmount());
//			} else {
//				applicationNoAndTxnAmountDB = consumerAppCancellationRefundAmountRepository
//						.findByApplicationNoAndTxnAmount(refundAmount.getConsumerApplicationNo(),
//								erpEstimateDB.getTotalBalanceDepositAmount());
//			}
//
//			if (erpEstimateDB.getCgst() != null) {
//
//				BigDecimal cgst = erpEstimateDB.getCgst();
//				BigDecimal sgst = erpEstimateDB.getSgst();
//				BigDecimal totalCgstSgst = cgst.add(sgst);
//				refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount().subtract(totalCgstSgst);
//			} else {
//				refundableAmount = applicationNoAndTxnAmountDB.getTxnAmount();
//			}
//			applicationNoAndTxnAmountDB.setRefundableAmount(refundableAmount);
//			consumerAppCancellationRefundAmountRepository.save(applicationNoAndTxnAmountDB);
//			totalWapaskrneWalaPaisa = totalWapaskrneWalaPaisa.add(refundableAmount);
//
//		}
//		refundAmount.setRefundAmount(totalWapaskrneWalaPaisa);
//		refundAmount.setConsumerApplicationNo(consumerApplicationDetail.getConsumerApplicationNo());
//		refundAmount.setConsumerAppId(consumerApplicationDetail.getConsumerApplicationId());
//		refundAmount.setRefundType("Cancellation_Amount");
//		double digit = Math.random();
//		double digit1 = Math.random();
//		String valueOf = String.valueOf(digit);
//		String valueOf1 = String.valueOf(digit1);
//		String substring = valueOf.substring(2, 8);
//		String substring1 = valueOf1.substring(2, 8);
//		refundAmount.setRefundVoucherNo(substring + substring1);
//		RefundAmount refundAmntDB = refundAmountRepository.save(refundAmount);
//		if (Objects.isNull(refundAmntDB)) {
//			throw new ConsumerApplicationDetailException(new Response(HttpCode.NULL_OBJECT, "Data Not Saved"));
//		} else {
//			consumerApplicationDetail.setApplicationStatus(applicationStatusRepository
//					.findById(ApplicationStatusEnum.APPLICATION_PENDING_AT_DGM_FOR_REFUND.getId()).get());
//			consumerApplictionDetailRepository.save(consumerApplicationDetail);
//			return refundAmntDB;
//		}
//
//	}

	@Autowired
	ContractorForBidRepository contractorForBidRepository;

	@GetMapping("/contractorMobileNo")
	public void contractorMobileNo() {
		contractorForBidRepository.findAllApplication().stream().forEach(con -> {
			Optional<ContractorForBid> first = contractorForBidRepository
					.findAllContractorNameAndPincode(con.getContractorName(), con.getCompanyPinCode()).stream()
					.findFirst();
			if (first.isPresent()) {
				System.err.println("Applicaiton : " + con.getConsumerApplicationNo() + "Contractor Name : "
						+ con.getContractorName() + "existed Mobile No. : " + con.getContactNo());
				System.err.println(
						"contact no Applicaiton : " + first.get().getConsumerApplicationNo() + "Contractor Name : "
								+ con.getContractorName() + "existed Mobile No. : " + first.get().getContactNo());

			}
		});

	}

//	Convert a List of Strings to Uppercase
	public static List<String> convertStringToUppercase(List<String> str) {

		return str.stream().map(String::toUpperCase).collect(Collectors.toList());

	}

//	Find the First Element Greater Than 50
	public static Optional<Integer> findFirstElmentGreaterThan50(List<Integer> x) {

		return x.stream().filter(n -> n > 50).findAny();
	}

//	Count Strings Starting with "A"
	public static int stringStartsWithA() {
		List<String> words = Arrays.asList("Apple", "Banana", "Avocado", "Mango", "Apricot");
		return words.stream().filter(n -> n.startsWith("A")).collect(Collectors.toList()).size();

	}

//	Find the Maximum and Minimum Numbers in a List
	public static void findMinMax() {
		List<Integer> numbers = Arrays.asList(5, 20, 8, 40, 15);

		Optional<Integer> max = numbers.stream().max(Integer::compareTo);

		Optional<Integer> min = numbers.stream().min(Integer::compareTo);
		System.err.println("Max no. : " + max);

		System.err.println("min no. : " + min);

		System.err
				.println("Max no. via sorting in descending : " + numbers.stream().sorted((a, b) -> b - a).findFirst());
		System.err.println("Min no. via sorting in ascending : " + numbers.stream().sorted((a, b) -> a - b).findAny());
	}

//	Remove Duplicates from a List
	public static void removeDuplicate() {
		List<Integer> numbers = Arrays.asList(10, 20, 10, 30, 40, 30, 50);
		System.err.println("list  duplicates : " + numbers);
		System.err.println(
				"list after removing duplicates : " + numbers.stream().distinct().collect(Collectors.toList()));

	}

//	Sort a List in Ascending and Descending Order
	public static void sortListInAscendingDescending() {
		List<Integer> numbers = Arrays.asList(5, 3, 8, 1, 9, 2);
		System.err
				.println("Sorted List in Ascending orders: " + numbers.stream().sorted().collect(Collectors.toList()));

		System.err.println("Sorted List in Descending orders: "
				+ numbers.stream().sorted((a, b) -> b - a).collect(Collectors.toList()));
	}

//	Convert List to Map
	public static void convertListToMap() {
		List<Employee> employees = Arrays.asList(new Employee(101, "Alice"), new Employee(102, "Bob"),
				new Employee(103, "Charlie"));

		System.err.println("List : " + new Gson().toJson(employees));

		System.err.println("MAP" + employees.stream().collect(Collectors.toMap(Employee::getId, Employee::getName)));
	}

//	Find the Second-Highest Number
	public static void findSecondHighestNumber() {
		List<Integer> numbers = Arrays.asList(10, 20, 30, 40, 50);
		System.err.println("second lowest number is : " + numbers.stream().sorted().skip(1).findFirst().orElse(null));

		System.err
				.println("second highest number is : " + numbers.stream().sorted((a, b) -> b - a).skip(1).findFirst());
	}

//	Group Elements by Length
	public static void groupElementByLength() {
		List<String> words = Arrays.asList("apple", "bat", "banana", "car", "dog", "elephant", "bat");
		words.stream().collect(Collectors.groupingBy(String::length));
		System.err
				.println("String Grouped by length : " + words.stream().collect(Collectors.groupingBy(String::length)));
	}

//	Find Words Starting with "A" and Convert to Uppercase
	public static void wordStartWithAandConvertUppercase() {
		List<String> words = Arrays.asList("Apple", "Banana", "Avocado", "Mango", "Apricot", "Cherry");
		words.stream().filter(wo -> wo.startsWith("A")).map(String::toUpperCase);
		System.err.println("Words Starting with \"A\" and Convert to Uppercase : " + words.stream()
				.filter(wo -> wo.startsWith("A")).map(String::toUpperCase).collect(Collectors.toList()));
	}

//	Find the Longest Word in a List
	public static void longestWordInList() {
		List<String> words = Arrays.asList("elephant", "cat", "giraffe", "hippopotamus", "dog");
		words.stream().map(String::length).max(Integer::compareTo).get();
		System.err.println(
				"Longest Word in a List : " + words.stream().map(String::length).max(Integer::compareTo).get());
	}

//	 Find Duplicate Elements in a List
	public static void findDuplicateElements() {
		List<Integer> numbers = Arrays.asList(1, 2, 100005, 3, 4, 2, 5, 6, 3, 7, 8, 100, 1);
		int[] hash = new int[100000 + 1];

		for (int ele : numbers) {
			hash[ele]++;
		}

//		System.out.println(new Gson().toJson(hash));

		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < hash.length; i++) {
			if (hash[i] > 1) {
				result.add(i);
			}
		}

		System.out.println(new Gson().toJson(result));
	}

//	Find Numbers Greater than 50 and Sort in Descending Order
	public static void numberGreatedThan50InDescendingOrder() {
		List<Integer> numbers = Arrays.asList(10, 55, 75, 30, 95, 50, 100, 45);
		System.err.println("numberGreatedThan50InDescendingOrder : "
				+ numbers.stream().filter(n -> n > 50).sorted((a, b) -> b - a).collect(Collectors.toList()));

	}

//	Count Even and Odd Numbers in a List
	public static void countEvenOddNumbers() {
		List<Integer> numbers = Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40, 45);
		numbers.stream().filter(n -> n % 2 == 0).count();
		System.err.println("Even number count : " + numbers.stream().filter(n -> n % 2 == 0).count());
		System.err.println("Odd number count : " + numbers.stream().filter(n -> n % 2 != 0).count());
	}

//	Find the First Non-Repeating Character in a String
//	public static void firstNonRepeatingCharacter() {
//		String str = "swiss";
//		String dummy =null;
//		char[] charArray = str.toCharArray();
//		for(char c : charArray) {
//			if(dummy.contains(str)) {
//				
//			}
//		}
//	}

//	Find the Sum of Square of All Odd Numbers
	public static void sumOfSquareAllOddNumbers() {
		int sum = 0;
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.stream().filter(n -> n % 2 != 0).map(i -> i * i).reduce(0, Integer::sum);

		System.err.println(
				"The sum is : " + numbers.stream().filter(n -> n % 2 != 0).map(i -> i * i).reduce(0, Integer::sum));

//		for (int i : numbers) {
//			if (i % 2 != 0) {
//				sum += i * i;
//			}
//		}
//
//		System.err.println("The sum is : " + sum);

	}

	public static void m111() {
		String s = "monika mohan sohan ram mena kartik rehuk meme";
//		List<String> str = Arrays.asList("monika mohan sohan ram mena kartik rehuk meme");
		String[] split = s.split(" ");
		List<String> str = Arrays.asList(split);

		List<String> collect = str.stream().filter(n -> n.startsWith("m")).collect(Collectors.toList());

	}

	public static void sortingAnArray() {
		int[] arr = { 5, 2, 8, 1, 3 };
		String[] names = { "John", "Alice", "Bob" };
		Integer[] arr2 = { 4, 1, 3, 9, 2 };

		Arrays.sort(arr);
		System.err.println(Arrays.toString(arr));

		Arrays.sort(names);
		System.err.println(Arrays.toString(names));

		Arrays.sort(arr2);
		System.err.println(Arrays.toString(arr2));

		int[] array = Arrays.stream(arr).sorted().toArray();

		System.err.println(Arrays.toString(array));

//		 sort array in descending order
		Arrays.sort(arr2, Comparator.reverseOrder());

		System.err.println(Arrays.toString(arr2));
	}

//	largest element in an array
	public static void largestElementInArray() {
		int[] arr = { 4, 1, 3, 9, 2 };
		Arrays.stream(arr).sorted().findFirst();
		System.err.println(Arrays.stream(arr).boxed().sorted((a, b) -> b - a).findFirst().orElse(null));

		System.err.println("largest element by max() : " + Arrays.stream(arr).max().orElseThrow(null));
		System.err.println("smallest element by min() : " + Arrays.stream(arr).min().orElseThrow(null));
	}

//	Program to count the number of words in string
	public static void numberOfWordsCountInString() {
		String s = "monika mohan sohan ram mena kartik rehuk meme";
		String[] split = s.trim().split("\\s+");

		System.out.println("Total words: " + split.length);

		;
		System.err.println("Total words via stream : " + Arrays.stream(s.trim().split("\\s+")).count());

	}

//	Occurance of character in given string
	public static void occuranceOfCharacterInString() {
		String str = "hello World";

		// Count character occurrences using Streams
		Map<Character, Long> charCountMap = str.toLowerCase().chars().mapToObj(c -> (char) c) // Convert IntStream to
																								// Stream<Character>
				.filter(c -> c != ' ').collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		System.out.println("Character Occurrences: " + charCountMap);
	}

//	remove duplicates from list
	public static void removeDuplicates() {
		List<Integer> numbers = Arrays.asList(10, 20, 30, 10, 40, 50, 20, 60);
		System.err.println("The original list is : " + numbers);

		System.err.println(
				" duplicates remove using distinct() : " + numbers.stream().distinct().collect(Collectors.toList()));

		System.err.println(" duplicates remove using set : " + numbers.stream().collect(Collectors.toSet()));

		System.err.println(" duplicates remove using HashSet : "
				+ numbers.stream().collect(Collectors.toCollection(LinkedHashSet::new)));
		;
	}

//	Sorting of Array
	public static void sortingOfArray() {
		int[] arr = { 4, 1, 3, 9, 2 };

		Arrays.sort(arr);

		System.err.println("sorted array : " + Arrays.toString(arr));
	}

	public static double upperRound(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		double scale = Math.pow(10, places);
		return Math.ceil(value);
	}

	
	

	public static void main(String[] args) {
	
		  String name = "charitrahahahaulla";

		
		
		Map<Character, Long> collect = name.chars().mapToObj(a-> (char) a).collect(Collectors.groupingBy(Function.identity(),  LinkedHashMap::new ,Collectors.counting()));
		name.chars().mapToObj(a-> (char) a).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap ::  new,Collectors.counting()));
	}                                                                                    
}

class Employee {
	int id;
	String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}