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

import com.new_vcs_conflicts.bubbledata.Bubble;
import com.new_vcs_conflicts.bubbledata.BubbleId;


/**
 * ServiceImpl object for domain model class Bubble.
 *
 * @see Bubble
 */
@Service("bubbledata.BubbleService")
@Validated
public class BubbleServiceImpl implements BubbleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BubbleServiceImpl.class);


    @Autowired
    @Qualifier("bubbledata.BubbleDao")
    private WMGenericDao<Bubble, BubbleId> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Bubble, BubbleId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "bubbledataTransactionManager")
    @Override
	public Bubble create(Bubble bubble) {
        LOGGER.debug("Creating a new Bubble with information: {}", bubble);

        Bubble bubbleCreated = this.wmGenericDao.create(bubble);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(bubbleCreated);
    }

	@Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Bubble getById(BubbleId bubbleId) throws EntityNotFoundException {
        LOGGER.debug("Finding Bubble by id: {}", bubbleId);
        return this.wmGenericDao.findById(bubbleId);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Bubble findById(BubbleId bubbleId) {
        LOGGER.debug("Finding Bubble by id: {}", bubbleId);
        try {
            return this.wmGenericDao.findById(bubbleId);
        } catch(EntityNotFoundException ex) {
            LOGGER.debug("No Bubble found with id: {}", bubbleId, ex);
            return null;
        }
    }


	@Transactional(rollbackFor = EntityNotFoundException.class, value = "bubbledataTransactionManager")
	@Override
	public Bubble update(Bubble bubble) throws EntityNotFoundException {
        LOGGER.debug("Updating Bubble with information: {}", bubble);

        this.wmGenericDao.update(bubble);
        this.wmGenericDao.refresh(bubble);

        return bubble;
    }

    @Transactional(value = "bubbledataTransactionManager")
	@Override
	public Bubble delete(BubbleId bubbleId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Bubble with id: {}", bubbleId);
        Bubble deleted = this.wmGenericDao.findById(bubbleId);
        if (deleted == null) {
            LOGGER.debug("No Bubble found with id: {}", bubbleId);
            throw new EntityNotFoundException(String.valueOf(bubbleId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "bubbledataTransactionManager")
	@Override
	public void delete(Bubble bubble) {
        LOGGER.debug("Deleting Bubble with {}", bubble);
        this.wmGenericDao.delete(bubble);
    }

	@Transactional(readOnly = true, value = "bubbledataTransactionManager")
	@Override
	public Page<Bubble> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Bubbles");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
    @Override
    public Page<Bubble> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Bubbles");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "bubbledataTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service bubbledata for table Bubble to {} format", exportType);
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

