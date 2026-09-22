import { Input, Label } from "reactstrap";

export default function FormSelectField({ name, label, value, onChange, options, required = true }) {
  return (
    <div className="custom-form-input">
      <Label for={name} className="custom-form-input-label">{label}</Label>
      <Input
        type="select"
        required={required}
        name={name}
        id={name}
        value={value ?? ""}
        onChange={onChange}
        className="custom-input"
      >
        <option value="">None</option>
        {options.map((o) => <option key={o.value} value={o.value}>{o.label}</option>)}
      </Input>
    </div>
  );
}