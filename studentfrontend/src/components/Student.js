import * as React from "react";
import {
  Box,
  TextField,
  Container,
  Paper,
  Button,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Table,
  TableHead,
  TableBody,
  TableRow,
  TableCell,
  TableContainer,
  Stack,
  IconButton,
  Snackbar,
  Alert
} from "@mui/material";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import DeleteIcon from "@mui/icons-material/Delete";


export default function Student() {
  const paperStyle = { padding: "50px 20px", width: "80%", margin: "20px auto" };
  const [name, setName] = useState("");
  const [address, setAddress] = useState("");
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [newCourseId, setNewCourseId] = useState("");
  const [error, setError] = useState("");

  // ✅ Studenten abrufen
  const fetchStudents = () => {
    fetch("http://localhost:8080/student/getall")
      .then((response) => response.json())
      .then((result) => {
        setStudents(result);
      });
  };

  useEffect(() => {
    fetchStudents();
    fetch("http://localhost:8080/course/getall")
      .then((response) => response.json())
      .then((result) => setCourses(result));
  }, []);

  // ✅ Neuen Studenten hinzufügen + Kurse direkt verknüpfen
  const handleClick = (e) => {
    e.preventDefault();

    // Überprüfen, ob Name und Adresse ausgefüllt sind
    if (!name || !address) {
      setError("Name und Adresse dürfen nicht leer sein.");
      return;
    }

    // Überprüfen, ob mindestens ein Kurs ausgewählt wurde
    if (selectedCourses.length === 0) {
      setError("Es muss mindestens ein Kurs hinzugefügt werden.");
      return;
    }

    const student = {
      name,
      address,
      courses: selectedCourses.map((id) => ({ id: parseInt(id) }))
    };

    fetch("http://localhost:8080/student/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(student)
    }).then(() => {
      console.log("Neuer Student hinzugefügt");
      setName("");
      setAddress("");
      setSelectedCourses([]);
      fetchStudents();
    });
  };

  // ✅ Studenten löschen
  const handleDelete = (studentId) => {
    fetch(`http://localhost:8080/student/delete/${studentId}`, {
      method: "DELETE"
    }).then(() => {
      console.log(`Student ${studentId} gelöscht`);
      fetchStudents();
    });
  };

  // Kurs löschen
  const handleDeleteCourse = (studentId, courseId) => {
    const student = students.find((s) => s.id === studentId);
    const newSelectedCourses = student.courses.filter((course) => course.id !== courseId).map((course) => course.id);
    setSelectedCourses(newSelectedCourses);

    // Jetzt aktualisiere den Studenten im Backend
    handleUpdateStudentCourse(studentId, newSelectedCourses);
  };

  // Kurs hinzufügen
  const handleAddCourse = (studentId) => {
    if (!newCourseId) return;

    // Überprüfen, ob der Kurs bereits in der Liste der ausgewählten Kurse vorhanden ist
    if (selectedCourses.includes(newCourseId)) {
      setError("Kurs ist bereits hinzugefügt.");
      return;
    }

    const newSelectedCourses = [...selectedCourses, newCourseId];
    setSelectedCourses(newSelectedCourses);

    // Jetzt aktualisiere den Studenten im Backend
    handleUpdateStudentCourse(studentId, newSelectedCourses);
  };

  // ✅ Studenten-Kurszuweisung aktualisieren
  const handleUpdateStudentCourse = (studentId, newSelectedCourses) => {
    // Umwandeln der IDs (als String oder Zahl) in ein Array von Objekten, das die erwartete Struktur hat
    const coursesPayload = newSelectedCourses.map(courseId => ({ id: parseInt(courseId) }));
    
    fetch(`http://localhost:8080/student/${studentId}/update-courses`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(coursesPayload)
    })
      .then(response => {
        if (!response.ok) {
          return response.json().then(error => { throw new Error(error.error); });
        }
        return response.json();
      })
      .then(() => {
        console.log(`Student ${studentId} Kurse aktualisiert`);
        fetchStudents();
      })
      .catch(error => {
        console.error("Fehler beim Aktualisieren der Kurse:", error);
      });
  };

  return (
    <Container>
      {/* Student hinzufügen */}
      <Paper elevation={4} style={paperStyle}>
        <Typography variant="h4" color="primary" align="center">
          Student Formular
        </Typography>
        <Box component="form" sx={{ display: "flex", flexDirection: "column", gap: 2 }} noValidate autoComplete="off">
          <TextField label="Student Name" variant="outlined" fullWidth value={name} onChange={(e) => setName(e.target.value)} />
          <TextField label="Student Address" variant="outlined" fullWidth value={address} onChange={(e) => setAddress(e.target.value)} />

          {/* Kursauswahl */}
          <FormControl fullWidth>
            <InputLabel>Kurse auswählen</InputLabel>
            <Select multiple value={selectedCourses} onChange={(e) => setSelectedCourses(e.target.value)}>
              {courses.map((course) => (
                <MenuItem key={course.id} value={course.id}>
                  {course.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Button variant="contained" color="success" onClick={handleClick} size="large">
            Student hinzufügen
          </Button>
        </Box>
      </Paper>

      {/* Studentenliste als Tabelle */}
      <Typography variant="h4" color="primary" align="center" sx={{ marginTop: 3 }}>
        Studenten
      </Typography>
      <TableContainer component={Paper} sx={{ marginTop: 3 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Adresse</TableCell>
              <TableCell>Kurse</TableCell>
              <TableCell>Aktionen</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {students.map((student) => (
              <TableRow key={student.id}>
                <TableCell>{student.id}</TableCell>
                <TableCell>{student.name}</TableCell>
                <TableCell>{student.address}</TableCell>
                <TableCell>
                  {student.courses && student.courses.length > 0 ? (
                    student.courses.map((course) => (
                      <div key={course.id} style={{ marginBottom: "10px" }}>
                        <IconButton
                          color="error"
                          onClick={() => handleDeleteCourse(student.id, course.id)}
                        >
                          <DeleteIcon />
                        </IconButton>
                        <span>{course.name}</span>
                      </div>
                    ))
                  ) : (
                    "Keine"
                  )}
                  {/* Dropdown-Menü um neuen Kurs hinzuzufügen */}
                    {/* Flex-Container für Dropdown und Button */}
                    <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
                  <FormControl fullWidth sx={{ maxWidth: 200, minWidth: 100, height: '40px' }}>
                    <InputLabel shrink style={{color: 'black', backgroundColor: 'white', px:3, }}>Kurs hinzufügen</InputLabel>
                    <Select
                      value={newCourseId}
                      onChange={(e) => setNewCourseId(e.target.value)}
                      displayEmpty // Damit das Label auch ohne Auswahl korrekt funktioniert
                      sx={{
                        height: '40px', // Höhe anpassen
                        padding: '5px 10px',
                        fontSize: '14px',
                        display: 'flex',
                        alignItems: 'center'
                      }}
                    >
                      <MenuItem value="" disabled>
                        Bitte wählen...
                      </MenuItem>
                      {courses.map((course) => (
                        <MenuItem key={course.id} value={course.id}>
                          {course.name}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                  <Button variant="contained" color="primary" sx={{ fontSize: '16px', height: '30px', width: '35px', minWidth: 0, padding: 0 }} onClick={() => handleAddCourse(student.id)}>
                    +
                  </Button>
                  </Box>
                </TableCell>
                <TableCell>
                  <Button variant="contained" color="error" onClick={() => handleDelete(student.id)}>
                    Löschen
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Link zur Kursseite */}
      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" component={Link} to="/courses">
          Kurse anzeigen
        </Button>
      </Stack>

      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" component={Link} to="/chat">
          Chatroom
        </Button>
      </Stack>

      {/* Snackbar für Fehlermeldungen */}
      <Snackbar open={!!error} autoHideDuration={6000} onClose={() => setError("")}>
        <Alert onClose={() => setError("")} severity="error" sx={{ width: '100%' }}>
          {error}
        </Alert>
      </Snackbar>
    </Container>
  );
}