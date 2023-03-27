package com.techproject.ClientService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class Files {
	
	    @Id
		@Column(name = "id")
	    private int id;
	    
		@Column(name = "name")
	    private String name;
		
	    @Lob
		@Column(name = "content")
	    private byte[] content;
	    
	    @Column(name = "contentType")
	    private String contentType;

	    
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public byte[] getContent() {
			return content;
		}
		public void setContent(byte[] content) {
			this.content = content;
		}
	    
	    

}
