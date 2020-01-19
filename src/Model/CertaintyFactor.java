/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author User
 */
public class CertaintyFactor {
    private Double nilaiCF;
    private Double cfpakar;
    private Double cfxPakar;
    private Double cfOld;
    private Double cfOld2;
    private Double cf1;
    private Double cf2;
    private Double maxValue;

    public Double getNilaiCF() {
        return nilaiCF;
    }

    public void setNilaiCF(Double NilaiCF) {
        this.nilaiCF = NilaiCF;
    }

    public Double getCfpakar() {
        return cfpakar;
    }

    public void setCfpakar(Double cfPakar) {
        this.cfpakar = cfPakar;
    }
    
    public Double getCfxPakar() {
        return cfxPakar;
    }

    public void setCfxPakar(Double CfxPakar) {
        this.cfxPakar = CfxPakar;
    }

    public Double getCfOld() {
        return cfOld;
    }

    public void setCfOld(Double CfOld) {
        this.cfOld = CfOld;
    }

    public Double getCfOld2() {
        return cfOld2;
    }

    public void setCfOld2(Double cfOld2) {
        this.cfOld2 = cfOld2;
    }

    public Double getCf1() {
        return cf1;
    }

    public void setCf1(Double Cf1) {
        this.cf1 = Cf1;
    }

    public Double getCf2() {
        return cf2;
    }

    public void setCf2(Double Cf2) {
        this.cf2 = Cf2;
    }
    
    public Double PerhitunganCFxPakar(Double nilaicf, Double cfPakar){
        cfxPakar = nilaicf*cfPakar;
        return cfxPakar;
    }
    
    public Double getHitungCfOld(Double cf1, Double cf2){
        cfOld = cf1+cf2*(1-cf1);
        return cfOld;
    }
    
    public Double getHitungCfOld2(Double cf3){
                
        cfOld2 = cfOld+cf3*(1-cfOld);
        //return cfOld2;
        return cfOld2;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }
}
