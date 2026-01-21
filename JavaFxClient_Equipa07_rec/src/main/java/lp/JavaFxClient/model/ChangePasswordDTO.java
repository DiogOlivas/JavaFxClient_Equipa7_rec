package lp.JavaFxClient.model;


public record ChangePasswordDTO (
		String oldPassword,
		String newPassword
		) {}
