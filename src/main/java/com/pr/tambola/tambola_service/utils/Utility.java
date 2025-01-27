package com.pr.tambola.tambola_service.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import com.pr.tambola.tambola_service.model.ErrorDetails;
import com.pr.tambola.tambola_service.model.Response;


public class Utility {
	public static void addErrorToResp(ErrorDetails error,Response resp) {
		List<ErrorDetails> li = null;
		li = resp.getErrorList();
		if(null==li) {
			li = new ArrayList<>();
		}
		li.add(error);
		resp.setErrorList(li);
	}
}
