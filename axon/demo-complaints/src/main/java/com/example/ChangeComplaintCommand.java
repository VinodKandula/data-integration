package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Deprecated
@AllArgsConstructor
@NoArgsConstructor
public class ChangeComplaintCommand {

	private String id;

	private String company;

	private String description;

}
