import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, Table } from "reactstrap";
import * as usersApi from "../../services/users";
import "../../static/css/admin/adminPage.css";
import deleteFromList from "../../util/deleteFromList";
import useErrorModal from "../../hooks/useErrorModal";
import useFetchState from "../../hooks/useFetchState";
import UserRow from "./UserRow";

export default function UserListAdmin() {
  const { errorModal, showError } = useErrorModal();
  const [users, setUsers] = useFetchState([], usersApi.getAllUsers, [], {
    onError: showError,
  });
  const [alerts, setAlerts] = useState([]);

  return (
    <div className="admin-page-container">
      <h1 className="text-center">Users</h1>
      {alerts.map((a) => a.alert)}
      {errorModal}
      <Button color="success" tag={Link} to="/users/new">
        Add User
      </Button>
      <div>
        <Table aria-label="users" className="mt-4">
          <thead>
            <tr>
              <th>Username</th>
              <th>Authority</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <UserRow
                key={user.id}
                user={user}
                onDelete={() =>
                  deleteFromList(
                    usersApi.deleteUser,
                    user.id,
                    [users, setUsers],
                    [alerts, setAlerts],
                    { onError: showError }
                  )
                }
              />
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
}
