package com.kdt.jpa.domain.member.dto;

public class MemberRequest {

	public static class JoinMemberRequest {
		private String name;
		private int age;
		private String hobby;


		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public String getHobby() {
			return hobby;
		}

		public JoinMemberRequest(String name, int age, String hobby) {
			this.name = name;
			this.age = age;
			this.hobby = hobby;
		}
	}

	public static class UpdateMemberRequest {
		private String name;
		private int age;
		private String hobby;

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public String getHobby() {
			return hobby;
		}

		public UpdateMemberRequest(String name, int age, String hobby) {
			this.name = name;
			this.age = age;
			this.hobby = hobby;
		}
	}
}
