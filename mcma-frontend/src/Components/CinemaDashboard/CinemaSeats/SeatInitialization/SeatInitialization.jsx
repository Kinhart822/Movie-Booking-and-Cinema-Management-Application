import React, { useState } from "react";
import { X } from "lucide-react";
import "./SeatInitialization.css";
import { api } from "../../../../api/apiClient";

const SEAT_TYPES = {
  NOT_PLACEABLE: { name: "Not Placeable", color: "#424242", size: {width: 1, length: 1} },
  PLACEABLE: { name: "Placeable", color: "#4CAFEB", size: {width: 1, length: 1} },
  STANDARD: { name: "Standard", color: "#C0C0C0", size: {width: 1, length: 1} },
  VIP: { name: "VIP", color: "#FFD700", size: {width: 1, length: 1} },
  LOVERS: { name: "Lovers", color: "#FF4081", size: {width: 2, length: 1} },
  BED: { name: "Bed", color: "#6A0DAD", size: {width: 2, length: 3} },
};

const SeatInitialization = ({ screenId, onClose, onSuccess }) => {
  const [grid, setGrid] = useState(
    Array(10)
      .fill()
      .map(() => Array(15).fill("PLACEABLE"))
  );
  const [selectedType, setSelectedType] = useState("PLACEABLE");
  const [isInitializing, setIsInitializing] = useState(false);

  const handleCellClick = (row, col) => {
    const seatSize = SEAT_TYPES[selectedType].size;

    // For multi-seat types
    if (["LOVERS", "BED"].includes(selectedType)) {
      const maxRow = row + seatSize.width - 1;
      const maxCol = col + seatSize.length - 1;

      // Check boundaries
      if (maxRow >= grid.length || maxCol >= grid[0].length) {
        return; // Cannot place seat here
      }

      // Create new grid with multi-seat placement
      const newGrid = grid.map((r) => [...r]);
      const currentType = grid[row][col];
      for (let r = row; r <= maxRow; r++) {
        for (let c = col; c <= maxCol; c++) {
          if (grid[r][c] !== currentType) {
            return;
          }
        }
      }
      for (let r = row; r <= maxRow; r++) {
        for (let c = col; c <= maxCol; c++) {
          newGrid[r][c] = selectedType;
        }
      }
      setGrid(newGrid);
    } else {
      // Single seat placement
      const newGrid = grid.map((r, i) =>
        i === row ? r.map((cell, j) => (j === col ? selectedType : cell)) : r
      );
      setGrid(newGrid);
    }
  };

  const handleInitialize = async () => {
    setIsInitializing(true);
    try {
      const seatLayout = [];
      grid.forEach((row, rowIndex) => {
        row.forEach((typeId, colIndex) => {
          seatLayout.push({
            row: colIndex,
            col: rowIndex,
            typeId: typeId,
          });
        });
      });

      await api.seats.initializeSeats(screenId, seatLayout);
      onSuccess();
    } catch (error) {
      console.error("Failed to initialize seats:", error);
    } finally {
      setIsInitializing(false);
    }
  };

  // Helper function to check if a position is the root position for a multi-seat type
  const isRootPosition = (grid, row, col, typeId) => {
    const seatType = SEAT_TYPES[typeId];
    const seatSize = seatType?.size || { rows: 1, cols: 1 };

    // Check if any position above or to the left has the same typeId
    for (let r = row - seatSize.width + 1; r <= row; r++) {
      for (let c = col - seatSize.length + 1; c <= col; c++) {
        if (r >= 0 && c >= 0 && grid[r][c] === typeId) {
          if (r < row || c < col) {
            return false; // This is not the root position
          }
        }
      }
    }
    return true;
  };

  return (
    <div className="init-modal-overlay">
      <div className="init-modal-content">
        <div className="init-modal-header">
          <h2>Initialize Screen Seats</h2>
          <button className="close-button" onClick={onClose}>
            <X size={20} />
          </button>
        </div>

        <div className="init-legend">
          {Object.entries(SEAT_TYPES).map(([typeId, type]) => (
            <div
              key={typeId}
              className={`init-legend-item ${
                selectedType === typeId ? "active" : ""
              }`}
              onClick={() => setSelectedType(typeId)}
            >
              <div
                className="init-legend-color"
                style={{ backgroundColor: type.color }}
              />
              <span>{type.name}</span>
            </div>
          ))}
        </div>

        <div className="init-preview-grid">
          {grid.map((row, rowIndex) => (
            <div key={rowIndex} className="init-preview-row">
              {row.map((cell, colIndex) => (
                <div
                  key={`${rowIndex}-${colIndex}`}
                  className="init-preview-cell"
                  style={{ backgroundColor: SEAT_TYPES[cell].color }}
                  onClick={() => handleCellClick(rowIndex, colIndex)}
                />
              ))}
            </div>
          ))}
        </div>

        <div className="init-controls">
          <button
            className="init-button secondary"
            onClick={onClose}
            disabled={isInitializing}
          >
            Cancel
          </button>
          <button
            className="init-button primary"
            onClick={handleInitialize}
            disabled={isInitializing}
          >
            {isInitializing ? "Initializing..." : "Initialize Seats"}
          </button>
        </div>
      </div>
    </div>
  );
};

export default SeatInitialization;
