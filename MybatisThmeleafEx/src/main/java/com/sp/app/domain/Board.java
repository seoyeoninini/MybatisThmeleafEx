package com.sp.app.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private long num;
	private String name;
	private String pwd;
	private String subject;
	private String content;
	private String ipAddr;
	private String reg_date;
	private int hitCount;
}
