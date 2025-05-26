import React from "react";
import Timeline from "react-calendar-timeline";
import "react-calendar-timeline/style.css";

// Example data
const groups = [
  { id: 1, title: "Group 1" },
  { id: 2, title: "Group 2" },
];

const items = [
  {
    id: 1,
    group: 1,
    title: "Item 1",
    start_time: new Date(),
    end_time: new Date().setHours(new Date().getHours() + 2),
  },
  {
    id: 2,
    group: 2,
    title: "Item 2",
    start_time: new Date().setHours(new Date().getHours() - 1),
    end_time: new Date().setHours(new Date().getHours() + 1),
  },
];

const MyTimeline = () => {
  return (
    <Timeline
      groups={groups}
      items={items}
      defaultTimeStart={new Date().setHours(new Date().getHours() - 12)}
      defaultTimeEnd={new Date().setHours(new Date().getHours() + 12)}
    />
  );
};

export default MyTimeline;
