import { render, screen } from "../test-utils";
import userEvent from "@testing-library/user-event";
import MatchRow from "./MatchRow";

// Isolated test of a single row: no API calls, and the delete callback is a mock (vi.fn)
const privateMatch = {
  id: 4,
  name: "Partida privada ya comenzada",
  code: "1234",
  start: "2026-10-01T16:00:00",
  finish: null,
  status: "PLAYING",
  players: [
    { id: 5, username: "player2" },
    { id: 6, username: "player3" },
  ],
};

// A <tr> must be inside a table to be valid HTML
const renderRow = (match, onDelete = vi.fn()) =>
  render(
    <table>
      <tbody>
        <MatchRow match={match} onDelete={onDelete} />
      </tbody>
    </table>
  );

describe("MatchRow", () => {
  test("shows the name, the type, the players and the status of the match", () => {
    renderRow(privateMatch);
    expect(screen.getByRole("cell", { name: "Partida privada ya comenzada" })).toBeInTheDocument();
    expect(screen.getByRole("cell", { name: "Private" })).toBeInTheDocument();
    expect(screen.getByRole("cell", { name: "player2, player3" })).toBeInTheDocument();
    expect(screen.getByText("PLAYING")).toBeInTheDocument();
  });

  test("shows a dash when the match has no players", () => {
    renderRow({ ...privateMatch, code: null, players: [] });
    expect(screen.getByRole("cell", { name: "Public" })).toBeInTheDocument();
    // start is set, finish and players are empty
    expect(screen.getAllByRole("cell", { name: "-" })).toHaveLength(2);
  });

  test("calls onDelete when the delete button is clicked", async () => {
    const user = userEvent.setup();
    const onDelete = vi.fn();
    renderRow(privateMatch, onDelete);

    await user.click(screen.getByRole("button", { name: "delete-4" }));

    expect(onDelete).toHaveBeenCalledTimes(1);
  });
});
