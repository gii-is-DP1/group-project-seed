import { useState } from "react";
import { FormGroup, Input, Label } from "reactstrap";
import PropTypes from "prop-types";

const statuses = [
  { value: "PLAYING", label: "Started" },
  { value: "WAITING", label: "Waiting" },
  { value: "FINISHED", label: "Finished" },
];

/**
 * Advanced search box for the lobby (see the mockup in the live coding guide).
 * It notifies the parent with the filters to apply: {} | {name} | {status}.
 */
export default function MatchSearch({ onSearch }) {
  const [advanced, setAdvanced] = useState(false);
  const [mode, setMode] = useState("name");
  const [name, setName] = useState("");
  const [status, setStatus] = useState("WAITING");

  const notify = (newAdvanced, newMode, newName, newStatus) => {
    if (!newAdvanced) onSearch({});
    else if (newMode === "name") onSearch(newName ? { name: newName } : {});
    else onSearch({ status: newStatus });
  };

  return (
    <div className="p-3 mb-3 bg-light border rounded" aria-label="match-search">
      <FormGroup check>
        <Input
          id="advanced-search"
          type="checkbox"
          checked={advanced}
          onChange={(e) => {
            setAdvanced(e.target.checked);
            notify(e.target.checked, mode, name, status);
          }}
        />
        <Label check for="advanced-search">Advanced search</Label>
      </FormGroup>

      {advanced && (
        <>
          <div className="mt-2">
            {["name", "status"].map((m) => (
              <FormGroup check inline key={m}>
                <Input
                  id={"search-by-" + m}
                  type="radio"
                  name="search-mode"
                  checked={mode === m}
                  onChange={() => {
                    setMode(m);
                    notify(true, m, name, status);
                  }}
                />
                <Label check for={"search-by-" + m}>
                  {m === "name" ? "By name" : "By state"}
                </Label>
              </FormGroup>
            ))}
          </div>

          {mode === "name" ? (
            <Input
              className="mt-2"
              type="search"
              aria-label="match-name"
              placeholder="Name of the match"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
                notify(true, mode, e.target.value, status);
              }}
            />
          ) : (
            <div className="mt-2">
              {statuses.map((s) => (
                <FormGroup check inline key={s.value}>
                  <Input
                    id={"status-" + s.value}
                    type="radio"
                    name="search-status"
                    checked={status === s.value}
                    onChange={() => {
                      setStatus(s.value);
                      notify(true, mode, name, s.value);
                    }}
                  />
                  <Label check for={"status-" + s.value}>{s.label}</Label>
                </FormGroup>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
}

MatchSearch.propTypes = {
  onSearch: PropTypes.func.isRequired,
};
