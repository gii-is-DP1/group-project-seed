import { useState } from "react";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";
import getErrorModal from "../util/getErrorModal";
import { Button, ButtonGroup, Table } from "reactstrap";
import { Link } from "react-router-dom";

const jwt = tokenService.getLocalAccessToken();

export default function MatchList() {
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    const [matches, setMatches] = useFetchState(
      [],
      `/api/v1/matches`,
      jwt,
      setMessage,
      setVisible
    );

    const matchesList =
    matches.map((match) => {
        return (
          <tr key={match.id}>
            <td className="text-center">{match.name}</td>
            <td className="text-center">{match.code?"private":"public"}</td>
            <td className="text-center">{match.start?'waiting':match.start}</td>
            <td className="text-center">{match.finish?match.finish:''}</td>
            <td className="text-center">
              <ButtonGroup>
                <Button
                  size="sm"
                  color="primary"
                  aria-label={"edit-" + match.name}
                  tag={Link}
                  to={"/matches/" + match.id}
                >
                  Edit
                </Button>
                <Button
                  size="sm"
                  color="danger"
                  aria-label={"delete-" + match.name}
                  onClick={() => {
                    let confirmMessage = window.confirm("Are you sure you want to delete it?");

                    if(!confirmMessage) return;

                    fetch(`/api/v1/matches/${match.id}`, {
                      method: "DELETE",
                      headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${jwt}`,
                      },
                    })
                      .then((res) => {
                        if (res.status === 204) {
                          setMessage("Deleted successfully");
                          setVisible(true);
                          setMatches(matches.filter((m) => m.id!==match.id));
                        }
                      })
                      .catch((err) => {
                        setMessage(err.message);
                        setVisible(true);
                      });
                  }}
                >
                  Delete
                </Button>
              </ButtonGroup>
            </td>
          </tr>
        );
      });

      
  const modal = getErrorModal(setVisible, visible, message);

  return (
    <div>
      <div className="admin-page-container">
        <h1 className="text-center">Matches</h1>        
        {modal}
        <div className="float-right">
          <Button color="success" tag={Link} to="/matches/new">
            Add Match
          </Button>
        </div>
        <div>
          <Table aria-label="matches" className="mt-4">
            <thead>
              <tr>
                <th width="15%" className="text-center">Name</th>
                <th width="15%" className="text-center">Type</th>
                <th width="15%" className="text-center">Start</th>
                <th width="15%" className="text-center">Finish</th>
                <th width="30%" className="text-center">Actions</th>
              </tr>
            </thead>
            <tbody>{matchesList}</tbody>
          </Table>
        </div>
      </div>
    </div>
  );

    
}