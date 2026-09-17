import { Link } from "react-router-dom";
import { Button, ButtonGroup } from "reactstrap";
import PropTypes from "prop-types";

export default function UserRow({ user, onDelete }) {
  return (
    <tr>
      <td>{user.username}</td>
      <td>{user.authority.authority}</td>
      <td>
        <ButtonGroup>
          <Button
            size="sm"
            color="primary"
            aria-label={"edit-" + user.id}
            tag={Link}
            to={"/users/" + user.id}
          >
            Edit
          </Button>
          <Button
            size="sm"
            color="danger"
            aria-label={"delete-" + user.id}
            onClick={onDelete}
          >
            Delete
          </Button>
        </ButtonGroup>
      </td>
    </tr>
  );
}

// Optional to facilitate static checking
UserRow.propTypes = {
  user: PropTypes.shape({
    id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
    username: PropTypes.string.isRequired,
    authority: PropTypes.shape({ authority: PropTypes.string }).isRequired,
  }).isRequired,
  onDelete: PropTypes.func.isRequired,
};
