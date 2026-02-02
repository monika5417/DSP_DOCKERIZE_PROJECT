package com.mpcz.deposit_scheme.backend.api.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mpcz.deposit_scheme.backend.api.domain.AmountMatch;
import com.mpcz.deposit_scheme.backend.api.domain.ConsumerApplicationDetail;
import com.mpcz.deposit_scheme.backend.api.domain.ErpRev;
import com.mpcz.deposit_scheme.backend.api.repository.AmountMatchRepository;
import com.mpcz.deposit_scheme.backend.api.repository.ConsumerApplictionDetailRepository;

@RestController
@RequestMapping("/AmountMatch")
public class AmountMatchController {

	@Autowired
	private ConsumerApplictionDetailRepository consumerApplictionDetailRepository;

	@Autowired
	private AmountMatchRepository amountMatchRepository;

	@RequestMapping("/supervision")
	public void match() {

		List<ConsumerApplicationDetail> con = consumerApplictionDetailRepository.findByScemetypeId();
		for (ConsumerApplicationDetail consuemrApplicationDetails : con) {
			AmountMatch amtMatch = new AmountMatch();

			ResponseEntity<String> exchange = null;
			RestTemplate rest = new RestTemplate();
			JSONObject json = null;

			String url = "https://dsp.mpcz.in:8888/urjas/XXPA_PROJECTS_DSP_V/"
					+ consuemrApplicationDetails.getErpWorkFlowNumber();
			HttpEntity<ErpRev> httpEntity = new HttpEntity<>(null);

			exchange = rest.exchange(url, HttpMethod.GET, httpEntity, String.class);
			try {
				json = new JSONObject(exchange.getBody());
				if (json.get("statusCode").toString().equals("404")) {
					String url1 = "https://dsp.mpcz.in:8888/newerp/XXPA_PROJECTS_DSP_V/"
							+ consuemrApplicationDetails.getErpWorkFlowNumber();
					exchange = rest.exchange(url1, HttpMethod.GET, httpEntity, String.class);
					json = new JSONObject(exchange.getBody());
				}

				if (json.get("statusCode").toString().equals("200")) {
					JSONArray jsonArray = json.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {

						if (jsonArray.getJSONObject(i).getString("PROJECT_NUMBER") != null) {
							amtMatch.setErpNo(jsonArray.getJSONObject(i).getString("PROJECT_NUMBER"));
						}

						if (jsonArray.getJSONObject(i).getString("SUPERVISION_COST") != null) {

							BigDecimal newSuperVisionAmt = new BigDecimal(
									jsonArray.getJSONObject(i).getString("SUPERVISION_COST"));
							BigDecimal Cgst = newSuperVisionAmt.multiply(new BigDecimal(.09));
							BigDecimal Sgst = newSuperVisionAmt.multiply(new BigDecimal(.09));

							amtMatch.setSupAmt(newSuperVisionAmt);
							amtMatch.setCgst(Cgst);
							amtMatch.setSgst(Sgst);

							amtMatch.setTotalSupAmt(newSuperVisionAmt.add(Cgst).add(Sgst));

							amtMatch.setRoundSupAmt(round11(newSuperVisionAmt, 0));
							amtMatch.setrCgst(round11(Cgst, 0));
							amtMatch.setrSgst(round11(Sgst, 0));

							amtMatch.setTotalRoundSupAmt(
									round11(newSuperVisionAmt.add(round11(Cgst, 0)).add(round11(Sgst, 0)), 0));

							amtMatch.setConsuemrAppNo(consuemrApplicationDetails.getConsumerApplicationNo());
						}

					}

				}
			} catch (Exception e) {

			}
			amountMatchRepository.save(amtMatch);
		}
	}

	public static BigDecimal round11(BigDecimal value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = value;
		bd = bd.setScale(0, RoundingMode.HALF_UP);
		return bd;
	}
}