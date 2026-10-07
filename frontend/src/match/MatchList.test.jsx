import { render, screen } from "../test-utils";
import userEvent from "@testing-library/user-event";
import MatchList from "./MatchList";

describe("MatchList", () => {
  test("renders the heading, the search box and the table", async () => {
    render(<MatchList />);
    expect(screen.getByRole("heading", { name: /matches/i })).toBeInTheDocument();
    expect(screen.getByRole("table", { name: "matches" })).toBeInTheDocument();
    expect(screen.getByLabelText("Advanced search")).toBeInTheDocument();
  });

  test("renders the matches returned by the API", async () => {
    render(<MatchList />);
    expect(await screen.findByRole("cell", { name: "Fiesta para todos" })).toBeInTheDocument();
    expect(await screen.findByRole("cell", { name: "Partida privada ya comenzada" })).toBeInTheDocument();
    expect(screen.getByRole("cell", { name: "Public" })).toBeInTheDocument();
    expect(screen.getByRole("cell", { name: "Private" })).toBeInTheDocument();
    // header row + 2 matches
    expect(screen.getAllByRole("row")).toHaveLength(3);
  });

  test("filters the matches by state", async () => {
    const user = userEvent.setup();
    render(<MatchList />);
    await screen.findByRole("cell", { name: "Fiesta para todos" });

    await user.click(screen.getByLabelText("Advanced search"));
    await user.click(screen.getByLabelText("By state"));
    await user.click(screen.getByLabelText("Started"));

    expect(await screen.findByRole("cell", { name: "Partida privada ya comenzada" })).toBeInTheDocument();
    expect(screen.queryByRole("cell", { name: "Fiesta para todos" })).not.toBeInTheDocument();
  });
});
