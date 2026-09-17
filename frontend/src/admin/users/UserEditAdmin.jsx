import { Link, useNavigate, useParams } from "react-router-dom";
import { Form, Input, Label } from "reactstrap";
import * as usersApi from "../../services/users";
import "../../static/css/admin/adminPage.css";
import useErrorModal from "../../hooks/useErrorModal";
import useFetchState from "../../hooks/useFetchState";

export default function UserEditAdmin() {
  const emptyItem = {
    id: null,
    username: "",
    password: "",
    authority: null,
  };
  const { id } = useParams();
  const navigate = useNavigate();
  const { errorModal, showError } = useErrorModal();
  const [user, setUser] = useFetchState(
    emptyItem,
    () => usersApi.getUserById(id),
    [id],
    { skip: id === "new", onError: showError }
  );
  const [auths] = useFetchState([], usersApi.getUserAuthorities, [], {
    onError: showError,
  });

  function handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    if (name === "authority") {
      const auth = auths.find((a) => a.id === Number(value));
      setUser({ ...user, authority: auth });
    } else setUser({ ...user, [name]: value });
  }

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      if (user.id) await usersApi.updateUser(user.id, user);
      else await usersApi.createUser(user);
      navigate("/users");
    } catch (err) {
      showError(err.response?.data?.message ?? "Error saving user");
    }
  }

  const authOptions = auths.map((auth) => (
    <option key={auth.id} value={auth.id}>
      {auth.authority}
    </option>
  ));

  return (
    <div className="auth-page-container">
      {<h2>{user.id ? "Edit User" : "Add User"}</h2>}
      {errorModal}
      <div className="auth-form-container">
        <Form onSubmit={handleSubmit}>
          <div className="custom-form-input">
            <Label for="username" className="custom-form-input-label">
              Username
            </Label>
            <Input
              type="text"
              required
              name="username"
              id="username"
              value={user.username || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <div className="custom-form-input">
            <Label for="lastName" className="custom-form-input-label">
              Password{user.id ? " (leave blank to keep unchanged)" : ""}
            </Label>
            <Input
              type="password"
              required={!user.id}
              name="password"
              id="password"
              value={user.password || ""}
              onChange={handleChange}
              className="custom-input"
            />
          </div>
          <Label for="authority" className="custom-form-input-label">
            Authority
          </Label>
          <div className="custom-form-input">
            <Input
              type="select"
              disabled={!!user.id}
              required={!user.id}
              name="authority"
              id="authority"
              value={user.authority?.id || ""}
              onChange={handleChange}
              className="custom-input"
            >
              <option value="">None</option>
              {authOptions}
            </Input>
          </div>
          <div className="custom-button-row">
            <button className="auth-button">Save</button>
            <Link
              to={`/users`}
              className="auth-button"
              style={{ textDecoration: "none" }}
            >
              Cancel
            </Link>
          </div>
        </Form>
      </div>
    </div>
  );
}
