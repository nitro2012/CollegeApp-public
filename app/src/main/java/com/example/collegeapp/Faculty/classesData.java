package com.example.collegeapp.Faculty;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class classesData {
    private String className,section,teacherName,classId;
    private ArrayList<String> studentList;
    private ArrayList <feedData> fData;

    public classesData(String className, String section, String teacherName, String classId, ArrayList<String> studentList) {
        this.className = className;
        this.section = section;
        this.teacherName = teacherName;
        this.classId = classId;
        this.studentList = studentList;
    }
    public classesData(String className, String section, String teacherName, String classId, ArrayList<String> studentList,ArrayList <feedData> fData) {
        this.className = className;
        this.section = section;
        this.teacherName = teacherName;
        this.classId = classId;
        this.fData=fData;
        this.studentList = studentList;
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public ArrayList<String> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<String> studentList) {
        this.studentList = studentList;
    }

    public classesData() {
    }
    public void setfData(ArrayList <feedData> fData) {
        this.fData=fData;
    }
    public ArrayList<feedData> getfData() {
        return fData;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("className", className);
        result.put("section", section);
        result.put("teacherName", teacherName);
        result.put("classId", classId);
        result.put("studentList", studentList);
        result.put("fData", fData);

        return result;
    }
}
