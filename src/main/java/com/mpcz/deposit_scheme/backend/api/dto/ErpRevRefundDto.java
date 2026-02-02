package com.mpcz.deposit_scheme.backend.api.dto;

import java.math.BigDecimal;

public class ErpRevRefundDto {
   private BigDecimal newSupervisionAmnt;
   private BigDecimal remSupervisionAmnt;
   private BigDecimal oldCgst;
   private BigDecimal oldSgst;
   private BigDecimal newDepositAmnt;
   private BigDecimal remDepositAmnt;
   private BigDecimal newKvaAmnt;
   private BigDecimal remKvaAmnt;
   private BigDecimal newColonySlumAmnt;
   private BigDecimal remColonySlumAmnt;
   private BigDecimal newReturnAmnt;
   private BigDecimal remReturnAmnt;
   private BigDecimal refundableAmnt;

   public BigDecimal getNewReturnAmnt() {
      return this.newReturnAmnt;
   }

   public void setNewReturnAmnt(BigDecimal newReturnAmnt) {
      this.newReturnAmnt = newReturnAmnt;
   }

   public BigDecimal getRemReturnAmnt() {
      return this.remReturnAmnt;
   }

   public void setRemReturnAmnt(BigDecimal remReturnAmnt) {
      this.remReturnAmnt = remReturnAmnt;
   }

   public BigDecimal getNewColonySlumAmnt() {
      return this.newColonySlumAmnt;
   }

   public void setNewColonySlumAmnt(BigDecimal newColonySlumAmnt) {
      this.newColonySlumAmnt = newColonySlumAmnt;
   }

   public BigDecimal getRemColonySlumAmnt() {
      return this.remColonySlumAmnt;
   }

   public void setRemColonySlumAmnt(BigDecimal remColonySlumAmnt) {
      this.remColonySlumAmnt = remColonySlumAmnt;
   }

   public BigDecimal getRemSupervisionAmnt() {
      return this.remSupervisionAmnt;
   }

   public void setRemSupervisionAmnt(BigDecimal remSupervisionAmnt) {
      this.remSupervisionAmnt = remSupervisionAmnt;
   }

   public BigDecimal getRemDepositAmnt() {
      return this.remDepositAmnt;
   }

   public void setRemDepositAmnt(BigDecimal remDepositAmnt) {
      this.remDepositAmnt = remDepositAmnt;
   }

   public BigDecimal getRemKvaAmnt() {
      return this.remKvaAmnt;
   }

   public void setRemKvaAmnt(BigDecimal remKvaAmnt) {
      this.remKvaAmnt = remKvaAmnt;
   }

   public BigDecimal getNewSupervisionAmnt() {
      return this.newSupervisionAmnt;
   }

   public void setNewSupervisionAmnt(BigDecimal newSupervisionAmnt) {
      this.newSupervisionAmnt = newSupervisionAmnt;
   }

   public BigDecimal getOldCgst() {
      return this.oldCgst;
   }

   public void setOldCgst(BigDecimal oldCgst) {
      this.oldCgst = oldCgst;
   }

   public BigDecimal getOldSgst() {
      return this.oldSgst;
   }

   public void setOldSgst(BigDecimal oldSgst) {
      this.oldSgst = oldSgst;
   }

   public BigDecimal getNewDepositAmnt() {
      return this.newDepositAmnt;
   }

   public void setNewDepositAmnt(BigDecimal newDepositAmnt) {
      this.newDepositAmnt = newDepositAmnt;
   }

   public BigDecimal getNewKvaAmnt() {
      return this.newKvaAmnt;
   }

   public void setNewKvaAmnt(BigDecimal newKvaAmnt) {
      this.newKvaAmnt = newKvaAmnt;
   }

   public BigDecimal getRefundableAmnt() {
      return this.refundableAmnt;
   }

   public void setRefundableAmnt(BigDecimal refundableAmnt) {
      this.refundableAmnt = refundableAmnt;
   }
}