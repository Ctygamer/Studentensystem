import React, { useState, useEffect } from "react";
import {
  Container,
  Paper,
  Button,
  TextField,
  Typography,
  Card,
  CardContent,
  CardActions,
  Stack,
  Box
} from "@mui/material";
import { useNavigate } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';


export default function Course() {
  const paperStyle = { padding: "50px 20px", width: "80%", margin: "20px auto" };
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [courses, setCourses] = useState([]);
  const navigate = useNavigate();

  // Fetch Courses
  const fetchCourses = () => {
    fetch("http://localhost:8080/course/getall")
      .then((response) => response.json())
      .then((result) => setCourses(result));
  };

  useEffect(() => {
    fetchCourses();
  }, []);

  // Add New Course
  const handleClick = (e) => {
    e.preventDefault();
    const course = { name, description };

    fetch("http://localhost:8080/course/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(course),
    }).then(() => {
      console.log("New course added");
      setName("");
      setDescription("");
      fetchCourses();
    });
  };

  // Delete Course
  const handleDelete = (courseId) => {
    fetch(`http://localhost:8080/course/delete/${courseId}`, {
      method: "DELETE",
    }).then(() => {
      console.log(`Course ${courseId} deleted`);
      fetchCourses();
    });
  };

  return (
    <Container>
      {/* Add Course Section */}
      <Paper elevation={4} style={paperStyle}>
        <Typography variant="h4" color="primary" align="center">
          Kurse hinzufügen
        </Typography>
        <Box component="form" sx={{ display: "flex", flexDirection: "column", gap: 2 }} noValidate autoComplete="off">
          <TextField
            label="Kurs Name"
            variant="outlined"
            fullWidth
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <TextField
            label="Beschreibung"
            variant="outlined"
            fullWidth
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />

          <Button variant="contained" color="success" onClick={handleClick} size="large">
            Kurs hinzufügen
          </Button>
        </Box>
      </Paper>

      {/* Display Courses Section */}
      <Typography variant="h4" color="primary" align="center" sx={{ marginTop: 3 }}>
        Kurse
      </Typography>
      <Box display="flex" flexWrap="wrap" justifyContent="center" sx={{ marginTop: 2 }}>
        {courses.map((course) => (
          <Box key={course.id} sx={{ width: { xs: '100%', sm: '48%', md: '30%' }, margin: '1%' }}>
            <Card elevation={6} sx={{ padding: "15px", borderRadius: "8px", boxShadow: 3 }}>
              <CardContent>
                <Typography variant="h6" color="primary" sx={{ fontWeight: "bold" }}>
                  {course.name}
                </Typography>
                <Typography variant="body1" color="textSecondary" sx={{ marginTop: 1 }}>
                  {course.description}
                </Typography>
              </CardContent>

              <CardActions sx={{ display: 'flex', justifyContent: 'center' }}> {/* Zentriert den Button */}
                <Button 
                  variant="contained" 
                  color="error" 
                  onClick={() => handleDelete(course.id)}
                  sx={{
                    minWidth: '50px',  // Etwas breiter machen
                    width: '60px',     // Fixierte Breite für bessere Optik
                    height: '40px',    // Höhe beibehalten
                    padding: '0',      // Innenabstand minimieren
                    display: 'flex',   
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}
                >
                               <DeleteIcon sx={{ fontSize: '26px' }} /> {/* Icon etwas größer machen */}
                </Button>
              </CardActions>
            </Card>          
          </Box>
        ))}
      </Box>

      {/* Back to Students Button */}
      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" onClick={() => navigate("/")}>
          Zurück zu Studenten
        </Button>
      </Stack>

      <Stack direction="row" justifyContent="center" sx={{ marginTop: 3 }}>
        <Button variant="contained" color="primary" onClick={() => navigate("/chat")}>
          Chatroom
        </Button>
      </Stack>
    </Container>
  );
}
