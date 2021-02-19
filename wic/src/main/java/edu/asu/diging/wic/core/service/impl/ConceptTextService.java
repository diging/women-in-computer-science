package edu.asu.diging.wic.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.diging.wic.core.model.impl.ConceptText;
import edu.asu.diging.wic.core.repository.ConceptTextDatabaseRepository;
import edu.asu.diging.wic.core.service.IConceptTextService;

@Service
@Transactional
public class ConceptTextService implements IConceptTextService {
		
	@Autowired
	private ConceptTextDatabaseRepository conceptTextDatabaseConnection;
	
	/**
	 * <p>Method is used to add conceptText Object in database
	 * </p>
	 * @param ConceptText Object
	 * @return void
	 */
	@Override
	public void addText(ConceptText conceptText) {
		conceptTextDatabaseConnection.save(conceptText);
	}

	/**
	 * <p>Method is used to find all conceptText to show on particular page
	 * </p>
	 * @param Pagenumber 
	 * @return List of ConceptText belonging to that pageNumber
	 */
	@Override
	public List<ConceptText> findAll(String pageNumber) {
		
		Pageable pagination = PageRequest.of(Integer.parseInt(pageNumber)-1, 5);
		Page<ConceptText> data = conceptTextDatabaseConnection.findAll(pagination);

		List<ConceptText> allDataConverted = new ArrayList<ConceptText>();
		 if(data != null) {
			 data.getContent().forEach(i -> allDataConverted.add(i));
	     }
		return allDataConverted;
	}

	@Override
	public void deleteText(String id) {

		conceptTextDatabaseConnection.deleteById(Long.parseLong(id));
	}

	@Override
	public void updateText(ConceptText updatedForm, String modifiedBy) {

		Optional<ConceptText> data = conceptTextDatabaseConnection.findById(updatedForm.getId());
		ConceptText obj = data.get();
        obj.setText(updatedForm.getText());
        obj.setTitle(updatedForm.getTitle());
        obj.setAuthor(updatedForm.getAuthor());
        obj.setModifiedby(modifiedBy);
        obj.setModifiedOn(new Date().getTime()+"");
        
        conceptTextDatabaseConnection.save(obj);
	}
	
	@Override
	public ConceptText getConceptTextById(String id) {

		Optional<ConceptText> data = conceptTextDatabaseConnection.findById(Long.parseLong(id));
		return data.get();
	}
	
	@Override
	public int getCount() {

		return (int) conceptTextDatabaseConnection.count();
	}

}
