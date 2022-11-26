package com.duosec.duosecbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * User: Avinash Vijayvargiya
 * Date: 24-Nov-22
 * Time: 12:41 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationData {
    @NotNull
    private String companyUniqueId;
    private String employeeName;
    private String sortBy;
    private int page;
    private int size;
    private boolean sort;
}
