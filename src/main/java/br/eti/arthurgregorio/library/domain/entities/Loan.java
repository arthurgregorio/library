/*
 * Copyright 2017 Arthur Gregorio, AG.Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.eti.arthurgregorio.library.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author Arthur Gregorio
 *
 * @since 1.0.0
 * @version 1.0.0, 15/12/2017
 */
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "loans", schema = "loan")
public class Loan extends PersistentEntity {

}
