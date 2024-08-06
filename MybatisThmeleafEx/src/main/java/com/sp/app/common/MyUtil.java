package com.sp.app.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class MyUtil {
	/**
	 * 전체 페이지수를 구하는 메소드
	 * 
	 * @param dataCount		총 데이터 개수
	 * @param size			한 화면에 출력할 데이터 개수
	 * @return				총 페이지 수
	 */
	public int pageCount(int dataCount, int size) {
		if(dataCount <= 0) {
			return 0;
		}
	
		return dataCount / size + (dataCount % size > 0 ? 1 : 0);
	}
	
	/**
	 * 페이징(paging) 처리를 하는 메소드(GET 방식, a 태그를 이용하여 해당 페이지의 URL로 이동)
	 * 
	 * @param current_page	화면에 출력할 페이지 번호
	 * @param total_page	총 페이지 수
	 * @param list_url		페이지 번호에 link를 설정할 URL
	 * @return				페이징 처리 결과
	 */
	public String paging(int current_page, int total_page, String list_url) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int currentPageSetup;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		if(list_url.indexOf("?") != -1) {
			list_url += "&";
		} else {
			list_url += "?";
		}
		
		// currentPageSetup : 표시할첫페이지-1
		currentPageSetup = (current_page / numPerBlock) * numPerBlock;
		if(current_page % numPerBlock == 0) {
			currentPageSetup = currentPageSetup - numPerBlock;
		}

		sb.append("<div class='paginate'>");
		// 처음페이지, 이전(10페이지 전)
		n = current_page - numPerBlock;
		if(total_page > numPerBlock && currentPageSetup > 0) {
			sb.append("<a href='" + list_url + "page=1'>처음</a>");
			sb.append("<a href='" + list_url + "page=" + n + "'>이전</a>");
		}
		
		// 페이징
		page = currentPageSetup + 1;
		while(page <= total_page && page <= (currentPageSetup + numPerBlock)) {
			if(page == current_page) {
				sb.append("<span>" + page + "</span>");
			} else {
				sb.append("<a href='" + list_url + "page=" + page + "'>" + page + "</a>");
			}

			page++;
		}
		
		// 다음(10페이지 후), 마지막페이지
		n = current_page + numPerBlock;
		if(n > total_page) n = total_page;
		if(total_page - currentPageSetup > numPerBlock) {
			sb.append("<a href='" + list_url + "page=" + n + "'>다음</a>");
			sb.append("<a href='" + list_url + "page=" + total_page + "'>끝</a>");
		}
		sb.append("</div>");
	
		return sb.toString();
	}

	/**
	 * javascript를 이용하여 페이징 처리를하는 메소드 : javascript의 지정한 함수(methodName)를 호출
	 * 
	 * @param current_page	화면에 출력할 페이지 번호
	 * @param total_page	총 페이지 수
	 * @param methodName	호출할 자바스크립트 함수명
	 * @return				페이징 처리 결과
	 */
	public String pagingMethod(int current_page, int total_page, String methodName) {
		StringBuilder sb = new StringBuilder();

		int numPerBlock = 10;   // 리스트에 나타낼 페이지 수
		int currentPageSetUp;
		int n, page;
        
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
        
		// currentPageSetup : 표시할첫페이지-1
		currentPageSetUp = (current_page / numPerBlock) * numPerBlock;
		if (current_page % numPerBlock == 0) {
			currentPageSetUp = currentPageSetUp - numPerBlock;
		}

		sb.append("<div class='paginate'>");
        
		// 처음페이지, 이전(10페이지 전)
		n = current_page - numPerBlock;
		if ((total_page > numPerBlock) && (currentPageSetUp > 0)) {
			sb.append("<a onclick='" + methodName + "(1);'>처음</a>");
			sb.append("<a onclick='" + methodName + "(" + n + ");'>이전</a>");
		}

		// 페이지징
		page = currentPageSetUp + 1;
		while((page <= total_page) && (page <= currentPageSetUp + numPerBlock)) {
			if(page == current_page) {
				sb.append("<span>" + page + "</span>");
			} else {
			   sb.append("<a onclick='" + methodName + "(" + page + ");'>" + page + "</a>");
			}
			page++;
		}

		// 다음(10페이지 후), 마지막 페이지
		n = current_page + numPerBlock;
		if(n > total_page) n = total_page;
		if (total_page - currentPageSetUp > numPerBlock) {
			sb.append("<a onclick='" + methodName + "(" + n + ");'>다음</a>");
			sb.append("<a onclick='" + methodName + "(" + total_page + ");'>끝</a>");
		}
		sb.append("</div>");

		return sb.toString();
	}

	// 화면에 표시할 페이지를 중앙에 출력
	public String pagingUrl(int current_page, int total_page, String list_url) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		if(list_url.indexOf("?") != -1) {
			list_url += "&";
		} else {
			list_url += "?";
		}
		
		page = 1; // 출력할 시작 페이지
		if(current_page > (numPerBlock / 2) + 1) {
			page = current_page - (numPerBlock / 2) ;
			
			n = total_page - page;
			if( n < numPerBlock ) {
				page = total_page - numPerBlock + 1;
			}
			
			if(page < 1) page = 1;
		}
		
		sb.append("<div class='paginate'>");
		
		// 처음페이지
		if(page > 1) {
			sb.append("<a href='"+list_url+"page=1' title='처음'>&#x226A</a>");
		}

		// 이전(한페이지 전)
		n = current_page - 1;
		if(current_page > 1) {
			sb.append("<a href='"+list_url+"page="+n+"' title='이전'>&#x003C</a>");
		}

		n = page;
		while(page <= total_page && page < n + numPerBlock) {
			if(page == current_page) {
				sb.append("<span>"+page+"</span>");
			} else {
				sb.append("<a href='"+list_url+"page="+page+"'>"+page+"</a>");
			}
			page++;
		}

		// 다음(한페이지 다음)
		n = current_page + 1;
		if(current_page < total_page) {
			sb.append("<a href='"+list_url+"page="+n+"' title='다음'>&#x003E</a>");
		}

		// 마지막페이지
		if(page <= total_page) {
			sb.append("<a href='"+list_url+"page="+total_page+"' title='마지막'>&#x226B</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}    
	
	// 화면에 표시할 페이지를 중앙에 출력 : javascript 함수 호출
	public String pagingFunc(int current_page, int total_page, String methodName) {
		StringBuilder sb = new StringBuilder();
		
		int numPerBlock = 10;
		int n, page;
		
		if(current_page < 1 || total_page < current_page) {
			return "";
		}
		
		page = 1; // 출력할 시작 페이지
		if(current_page > (numPerBlock / 2) + 1) {
			page = current_page - (numPerBlock / 2) ;
			
			n = total_page - page;
			if( n < numPerBlock ) {
				page = total_page - numPerBlock + 1;
			}
			
			if(page < 1) page = 1;
		}
		
		sb.append("<div class='paginate'>");
		
		// 처음페이지
		if(page > 1) {
			sb.append("<a onclick='" + methodName + "(1);' title='처음'>&#x226A</a>");
		}

		// 이전(한페이지 전)
		n = current_page - 1;
		if(current_page > 1) {
			sb.append("<a onclick='" + methodName + "(" + n + ");' title='이전'>&#x003C</a>");
		}

		n = page;
		while(page <= total_page && page < n + numPerBlock) {
			if(page == current_page) {
				sb.append("<span>"+page+"</span>");
			} else {
				sb.append("<a onclick='" + methodName + "(" + page + ");' >"+page+"</a>");
			}
			page++;
		}

		// 다음(한페이지 다음)
		n = current_page + 1;
		if(current_page < total_page) {
			sb.append("<a onclick='" + methodName + "(" + n + ");' title='다음'>&#x003E</a>");
		}

		// 마지막페이지
		if(page <= total_page) {
			sb.append("<a onclick='" + methodName + "(" + total_page + ");' title='마지막'>&#x226B</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}    
    
	/**
	* 문자열에서 HTML 태그를 제거하는 메소드
	* 
	* @param str		HTML 태그를 제거할 문자열
	* @return			HTML 태그가 제거된 문자열
	*/
	public String removeHtmlTag(String str) {
		if(str == null || str.length() == 0) {
			return "";
		}

		String regex = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>";
		String result = str.replaceAll(regex, "");
		
		return result;
    }

	/**
	* HTML 문서에서 img 태그의 src 속성값을 추출하는 메소드
	* 
	* @param html		html 문자열
	* @return			추출된 src 속성값을 가지고 있는 List 객체 
	*/
	public List<String> getImgSrc(String html) {
		List<String> result = new ArrayList<String>();
		
		if(html == null || html.length() == 0) {
			return result;
		}

		String regex = "<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>";
		Pattern nonValidPattern = Pattern.compile(regex);

		Matcher matcher = nonValidPattern.matcher(html);
		while (matcher.find()) {
			result.add(matcher.group(1));
		}
		
		return result;
    }

	/**
	* 특수 문자를 HTML 문자로 변경 및 엔터를 <br>로 변경 하는 메소드
	* 
	* @param str		특수 문자를 HTML 문자로 변경할 문자열
	* @return			특수 문자가 HTML 문자로 변경된 문자열
	*/
	public String htmlSymbols(String str) {
		if(str == null || str.length() == 0) {
			return "";
		}

		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("<", "&lt;");
    	 
		str = str.replaceAll(" ", "&nbsp;"); // \\s를 사용할 경우 \n 아래에서 사용해야 한다.
		str = str.replaceAll("\n", "<br>");
    	 
		return str;
	}
     
	/**
	* 중간 이름 마스킹 처리
	* @param userName		이름
	* @return				마스킹 처리된 이름
	*/
	public String nameMasking(String userName) {
		String result = "";
    	 
		try {
			userName = userName.replaceAll("\\s", "");
			
			if(userName.length() < 2) {
				return userName;
			}
        	 
			String firstName, midName, lastName, s;
        	 
			firstName = userName.substring(0, 1); // 성
			midName = "";
			if(userName.length() > 2) {
				midName = userName.substring(1, userName.length()-1); // 중간이름
			}
			s = "";
			for(int i = 0; i<midName.length(); i++) {
				s += "*";
			}
			if(userName.length() > 2) {
				lastName = userName.substring(userName.length()-1, userName.length()); // 마지막 글자
			} else {
				lastName = "*";
			}
        	 
			result = firstName + s + lastName;
		} catch (Exception e) {
		}
    	 
		return result;
	}

	/**
	* 정규식을 이용하여 E-Mail을 검사하는 메소드
	* 
	* @param email		검사할 E-Mail
	* @return			올바른 E-Mail 이지의 여부
	*/
	public boolean isValidEmail(String email) {
		if (email == null) return false;
		
		boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
						email.trim());
		return b;
	}
}
