import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Table,
  TableBody,
  TableHead,
  TableRow,
  TableContainer,
  Paper,
  Typography,
  TableCell,
  tableCellClasses,
  styled,
} from "@mui/material/";
import "./ViewEmployees.css";

const StyledTableCell = styled(TableCell)(() => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: "#ababab",
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

/**
 * Main function for the ViewEmployees page that displays all employees in the database
 * @author VZ and Kevin
 * @returns table of employees with name, email and schedule
 */
export function ViewEmployees() {
  const [employees, setEmployees] = useState([]); // initial state set to empty array

  useEffect(() => {
    // function getAllEmployees() {
    axios // axios is a library that allows us to make HTTP requests
      .get("/api/employee")
      .then(function (response) {
        // if the request is successful
        console.log(response.data);
        setEmployees(response.data); // set the state to the data returned from the API
      })
      .catch(function (error) {
        // if the request fails
        console.log(error.response.data);
      });
  }, []);

  if (employees.length === 0) {
    return (
      <>
        <div>
          <h1 style={{ marginTop: 20, marginBottom: 20 }}>
            List&nbsp;of&nbsp;all&nbsp;Employees
          </h1>
        </div>
        <Typography>There are no employees at the moment.</Typography>
      </>
    );
  } else {
    return (
      <>
        <div>
          <h1 style={{ marginTop: 20, marginBottom: 20 }}>
            List&nbsp;of&nbsp;all&nbsp;Employees
          </h1>
        </div>
        <TableContainer
          component={Paper}
          sx={{
            display: "flex",
            justifyContent: "center",
            maxHeight: "500px",
            maxWidth: "1000px",
            boxShadow: 4,
            borderRadius: 1,
            my: 2,
            mx: "auto",
          }}
        >
          <Table stickyHeader aria-label="simple table">
            <TableHead>
              <TableRow>
                <StyledTableCell>
                  <Typography
                    sx={{
                      fontWeight: "bold",
                      fontSize: 18,
                    }}
                  >
                    Name
                  </Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Typography sx={{ fontWeight: "bold", fontSize: 18 }}>
                    Email
                  </Typography>
                </StyledTableCell>
                <StyledTableCell align="right">
                  <Typography sx={{ fontWeight: "bold", fontSize: 18 }}>
                    View&nbsp;Schedule
                  </Typography>
                </StyledTableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {employees.map((employee) => (
                <StyledTableRow
                  key={employee.museumUserId}
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <StyledTableCell>{employee.name}</StyledTableCell>
                  <StyledTableCell>{employee.email}</StyledTableCell>
                  <StyledTableCell align="right">
                    <a
                      href={`/schedule/${employee.museumUserId}`}
                      className="hover-underline-animation"
                    >
                      View&nbsp;{grammarCheck(employee.name)}&nbsp;schedule
                    </a>
                  </StyledTableCell>
                </StyledTableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </>
    );
  }
}

// Helper function to check if the name ends with an s and add an apostrophe depending on the case
function grammarCheck(name) {
  return name.charAt(name.length - 1) !== "s" ? name + "'s" : name + "'";
}
