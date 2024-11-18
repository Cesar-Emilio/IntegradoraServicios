// src/components/FormInput.jsx
import React from "react";

const FormInput = ({ label, type, value, onChange, placeholder }) => (
  <div className="form-input">
    <label>{label}</label>
    <input
      type={type}
      value={value}
      onChange={onChange}
      placeholder={placeholder}
      required
    />
  </div>
);

export default FormInput;
