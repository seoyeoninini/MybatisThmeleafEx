package com.sp.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sp.app.domain.Board;
import com.sp.app.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	private final BoardMapper dao;
	
	@Override
	public void insertBoard(Board dto) throws Exception {
		try {
			dao.insertBoard(dto);
		} catch (Exception e) {
			log.info("BoardService - insertBoard : ", e);
			throw e;
		}
	}

	@Override
	public int dataCount(Map<String, Object> map) {
		int result = 0;
		
		try {
			result = dao.dataCount(map);
		} catch (Exception e) {
			log.info("BoardService - dataCount : ", e);
		}
		
		return result;
	}

	@Override
	public List<Board> listBoard(Map<String, Object> map) {
		List<Board> list = null;
		
		try {
			list = dao.listBoard(map);
		} catch (Exception e) {
			log.info("BoardService - listBoard : ", e);
		}
		
		return list;
	}

	@Override
	public Board findById(long num) {
		Board dto = null;
		
		try {
			dto = dao.findById(num);
		} catch (Exception e) {
			log.info("BoardService - findById : ", e);
		}
		
		return dto;
	}

	@Override
	public void updateHitCount(long num) throws Exception {
		try {
			dao.updateHitCount(num);
		} catch (Exception e) {
			log.info("Service - updateHitCount : ", e);
			throw e;
		}
	}
	
	@Override
	public Board findByPrev(Map<String, Object> map) {
		Board dto = null;
		
		try {
			dto = dao.findByPrev(map);
		} catch (Exception e) {
			log.info("Service - findByPrev : ", e);
		}
		
		return dto;
	}

	@Override
	public Board findByNext(Map<String, Object> map) {
		Board dto = null;
		
		try {
			dto = dao.findByNext(map);
		} catch (Exception e) {
			log.info("BoardService - findByNext : ", e);
		}
		
		return dto;
	}
	
	@Override
	public void updateBoard(Board dto) throws Exception {
		try {
			dao.updateBoard(dto);
		} catch (Exception e) {
			log.info("BoardService - updateBoard : ", e);
			throw e;
		}
	}

	@Override
	public void deleteBoard(long num) throws Exception{
		try {
			dao.deleteBoard(num);
		} catch (Exception e) {
			log.info("BoardService - deleteBoard : ", e);
			throw e;
		}
	}
}
