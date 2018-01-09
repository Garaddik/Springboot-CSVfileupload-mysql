package com.dot.s3.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dot.s3.Student;
import com.dot.s3.dao.StudentDAO;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class UploadService {

	@Autowired
	private StudentDAO studentDAO;

	public List<Student> uploadFile(MultipartFile multipartFile) throws IOException {

		File file = convertMultiPartToFile(multipartFile);

		List<Student> mandatoryMissedList = new ArrayList<Student>();

		try (Reader reader = new FileReader(file);) {
			@SuppressWarnings("unchecked")
			CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(reader).withType(Student.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			List<Student> studentList = csvToBean.parse();

			Iterator<Student> studentListClone = studentList.iterator();

			while (studentListClone.hasNext()) {

				Student student = studentListClone.next();

				if (student.getPhoneNumber() == null || student.getPhoneNumber().isEmpty()
						|| student.getFirstName() == null || student.getFirstName().isEmpty()) {
					mandatoryMissedList.add(student);
					studentListClone.remove();
				}
			}

			studentDAO.batchStore(studentList);
		}
		return mandatoryMissedList;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	public List<Student> getStudents() {
		return studentDAO.getStudents();
	}
}