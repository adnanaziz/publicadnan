package net.homeip.jtjang.MileageRun;

import java.util.Comparator;

public class KayakDeal implements Comparator<KayakDeal> {

	private double price;
	private double ppm; // price per mile
	private int distance;
	private String origin;
	private String dest;
	private String departDate;
	private String returnDate;
	private String airline;

	public final void print() {
		System.out.println("Price=" + price + ", origin=" + origin + 
				", dest=" + dest + ", departDate=" + departDate + ", returnDate=" + returnDate + ", airline=" + airline);
	}
	
	public final double getPrice() {
		return price;
	}
	public final void setPrice(double price) {
		this.price = price;
	}
	public final double getPPM() {
		return ppm;
	}

	public final void setPPM(double ppm) {
		this.ppm = ppm;
	}

	public final int getDistance() {
		return distance;
	}

	public final void setDistance(int distance) {
		this.distance = distance;
	}

	public final String getOrigin() {
		return origin;
	}
	public final void setOrigin(String origin) {
		this.origin = origin;
	}
	public final String getDest() {
		return dest;
	}
	public final void setDest(String dest) {
		this.dest = dest;
	}
	public final String getDepartDate() {
		return departDate;
	}
	public final void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	public final String getReturnDate() {
		return returnDate;
	}
	public final void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public final String getAirline() {
		return airline;
	}
	public final void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Default order is in PPM (small to large).
	 */
	@Override
	public int compare(KayakDeal deal1, KayakDeal deal2) {
		return (deal1.ppm >= deal2.ppm) ? 1 : -1;
	}

}
