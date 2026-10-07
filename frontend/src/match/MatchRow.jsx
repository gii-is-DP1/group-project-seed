import { Badge, Button } from "reactstrap";
import PropTypes from "prop-types";

const statusColors = {
  WAITING: "success",
  PLAYING: "warning",
  FINISHED: "secondary",
};

const formatDate = (value) => (value ? new Date(value).toLocaleString() : "-");

export default function MatchRow({ match, onDelete }) {
  return (
    <tr>
      <td>{match.name}</td>
      <td>{match.code ? "Private" : "Public"}</td>
      <td>
        <Badge color={statusColors[match.status] ?? "light"}>{match.status}</Badge>
      </td>
      <td>{formatDate(match.start)}</td>
      <td>{formatDate(match.finish)}</td>
      <td>
        <Button
          size="sm"
          color="danger"
          aria-label={"delete-" + match.id}
          onClick={onDelete}
        >
          Delete
        </Button>
      </td>
    </tr>
  );
}

MatchRow.propTypes = {
  match: PropTypes.shape({
    id: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
    name: PropTypes.string.isRequired,
    code: PropTypes.string,
    status: PropTypes.oneOf(["WAITING", "PLAYING", "FINISHED"]),
    start: PropTypes.string,
    finish: PropTypes.string,
  }).isRequired,
  onDelete: PropTypes.func.isRequired,
};
