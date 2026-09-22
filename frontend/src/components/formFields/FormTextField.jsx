import { Input, Label } from "reactstrap";

export default function FormTextField({ name, label, value, onChange, type = "text", required = true }) {
  return (
    <div className="custom-form-input">
      <Label for={name} className="custom-form-input-label">{label}</Label>
      <Input
        type={type}
        required={required}
        name={name}
        id={name}
        value={value ?? ""}
        onChange={onChange}
        className="custom-input"
      />
    </div>
  );
}
