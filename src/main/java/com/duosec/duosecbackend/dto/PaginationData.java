package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Avinash Vijayvargiya
 * Date: 24-Nov-22
 * Time: 12:41 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationData {
    private String companyUniqueId;
    private String employeeName;
    private String sortBy;
    private int page;
    private int size;
    private Boolean sort;
}
