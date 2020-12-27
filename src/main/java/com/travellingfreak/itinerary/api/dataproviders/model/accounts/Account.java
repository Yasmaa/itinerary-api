package com.travellingfreak.itinerary.api.dataproviders.model.accounts;

import com.travellingfreak.itinerary.api.dataproviders.model.base.SoftDeleteWithAudit;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Date: 7/19/2020 Time: 2:22 PM
 */
@Entity
@Table(name = "accounts")
@SQLDelete(sql =
               "UPDATE Account " +
               "SET deleted = true " +
               "WHERE id = ?")
@Loader(namedQuery = "findAccountById")
@NamedQuery(name = "findAccountById", query =
    "SELECT a "+
    "FROM Account a " +
    "WHERE " +
    "      a.id = ?1 AND "+
    "      a.deleted = false")
@Where(clause = "deleted = false")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Account extends SoftDeleteWithAudit {
  @Id
  private String id;

  @Column(name = "display_name")
  private String displayName;

  private String email;

}
