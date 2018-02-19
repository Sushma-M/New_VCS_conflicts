/*\/**
 * Copyright (c) 2015-2016 wavemaker.com All Rights Reserved.
 * This software is the confidential and proprietary information of wavemaker-com * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with wavemaker.com *\/*/
package com.new_vcs_conflicts.bubbledata.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.new_vcs_conflicts.bubbledata.Table2ww;


/**
 * ServiceImpl object for domain model class Table2ww.
 *
 * @see Table2ww
 */
@Service("bubbledata.Table2wwService")
@Validated
public class Table2wwServiceImpl implements Table2wwService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Table2wwServiceImpl.class);


    @Autowired
    @Qualifier("bubbledata.Table2wwDao")
    private WMGenericDao<Table2ww, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Table2ww, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "bubbledataTransactionManager")
    @Override
	public Table2ww create(Table2ww table2ww) {
        LOGGER.debug("Creating a new Table2ww with information: {}", table2ww);

        Table2ww table2wwCreated = this.wmGenericDao.create(table2ww);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(table2wwCreated);
    }

	@Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Table2ww getById(Integer table2wwId) throws EntityNotFoundException {
        LOGGER.debug("Finding Table2ww by id: {}", table2wwId);
        return this.wmGenericDao.findById(table2wwId);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Table2ww findById(Integer table2wwId) {
        LOGGER.debug("Finding Table2ww by id: {}", table2wwId);
        try {
            return this.wmGenericDao.findById(table2wwId);
        } catch(EntityNotFoundException ex) {
            LOGGER.debug("No Table2ww found with id: {}", table2wwId, ex);
            return null;
        }
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "bubbledataTransactionManager")
	@Override
	public Table2ww update(Table2ww table2ww) throws EntityNotFoundException {
        LOGGER.debug("Updating Table2ww with information: {}", table2ww);

        this.wmGenericDao.update(table2ww);
        this.wmGenericDao.refresh(table2ww);

        return table2ww;
    }

    @Transactional(value = "bubbledataTransactionManager")
	@Override
	public Table2ww delete(Integer table2wwId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Table2ww with id: {}", table2wwId);
        Table2ww deleted = this.wmGenericDao.findById(table2wwId);
        if (deleted == null) {
            LOGGER.debug("No Table2ww found with id: {}", table2wwId);
            throw new EntityNotFoundException(String.valueOf(table2wwId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "bubbledataTransactionManager")
	@Override
	public void delete(Table2ww table2ww) {
        LOGGER.debug("Deleting Table2ww with {}", table2ww);
        this.wmGenericDao.delete(table2ww);
    }

	@Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Page<Table2ww> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Table2wws");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
    @Override
    public Page<Table2ww> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Table2wws");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service bubbledata for table Table2ww to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}

