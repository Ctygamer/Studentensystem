package com.canama.studentsystem.DTO;

import java.io.Serializable;

/**
 * Datenübertragungsobjekt für einen Kurs.
 *
 * Enthält die wesentlichen Daten eines Kurses, inklusive der eindeutigen ID,
 * des Namens sowie der Beschreibung.
 */
public record CourseDto(Integer id, String name, String description) implements Serializable {
}
