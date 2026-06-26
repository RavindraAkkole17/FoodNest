package com.tap.Model;

public class Restaurant {
		private int restaurantId;
		private String cuisineType;
		private String address;
		private int adminUserId;
		private String name;
		private float rating;
		private int isActive;
		private int deliveryTime;
		private String path;
		
		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public Restaurant() {
			// TODO Auto-generated constructor stub
		}

		public Restaurant(String name, String cuisineType, int deliveryTime, String address, int adminUserId,
				float rating, int isActive, String path) {
			super();
			this.name = name;
			this.cuisineType = cuisineType;
			this.deliveryTime = deliveryTime;
			this.address = address;
			this.adminUserId = adminUserId;
			this.rating = rating;
			this.isActive = isActive;
			this.path = path;		}
		
		

		public int getRestaurantId() {
			return restaurantId;
		}

		public void setRestaurantId(int restaurantId) {
			this.restaurantId = restaurantId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCuisineType() {
			return cuisineType;
		}

		public void setCuisineType(String cuisineType) {
			this.cuisineType = cuisineType;
		}

		public int getDeliveryTime() {
			return deliveryTime;
		}

		public void setDeliveryTime(int deliveryTime) {
			this.deliveryTime = deliveryTime;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getAdminUserId() {
			return adminUserId;
		}

		public void setAdminUserId(int adminUserId) {
			this.adminUserId = adminUserId;
		}

		public float getRating() {
			return rating;
		}

		public void setRating(float rating) {
			this.rating = rating;
		}

		public int getIsActive() {
			return isActive;
		}

		public void setIsActive(int isActive) {
			this.isActive = isActive;
		}

		@Override
		public String toString() {
			return "Restaurant [restaurantId=" + restaurantId + ", name=" + name + ", cuisineType=" + cuisineType
					+ ", deliveryTime=" + deliveryTime + ", address=" + address + ", adminUserId=" + adminUserId
					+ ", rating=" + rating + ", isActive=" + isActive + ", path=" + path + "]";
		}

		public Restaurant(int restaurantId, String name, String cuisineType, int deliveryTime, String address,
				int adminUserId, float rating, int isActive, String path) {
			super();
			this.restaurantId = restaurantId;
			this.name = name;
			this.cuisineType = cuisineType;
			this.deliveryTime = deliveryTime;
			this.address = address;
			this.adminUserId = adminUserId;
			this.rating = rating;
			this.isActive = isActive;
			this.path = path;
		}
		
		
	

}
