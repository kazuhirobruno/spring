package com.eventostec.api.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventostec.api.domain.coupon.Coupon;
import com.eventostec.api.domain.coupon.CouponRequestDTO;
import com.eventostec.api.domain.event.Event;
import com.eventostec.api.repositories.CouponRepository;
import com.eventostec.api.repositories.EventRepository;

@Service
public class CouponService {

  @Autowired
  private CouponRepository couponRepository;

  @Autowired
  private EventRepository eventRepository;

  public Coupon addCouponToEvent(UUID eventId, CouponRequestDTO data) {
    Event event = eventRepository.findById(eventId).orElseThrow(
        () -> new IllegalArgumentException("Event not found"));
    Coupon newCoupon = new Coupon();
    newCoupon.setCode(data.code());
    newCoupon.setDiscount(data.discount());
    newCoupon.setValid(new Date(data.valid()));
    newCoupon.setEvent(event);
    return couponRepository.save(newCoupon);
  }

  public List<Coupon> consultCoupons(UUID eventId, Date currentDate) {
    return couponRepository.findEventIdAndValidAfter(eventId, currentDate);
  }
}
