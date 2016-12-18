package org.thiki.kanban.cardTags;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.card.CardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


/**
 * Created by xubt on 11/14/16.
 */
@Service
public class CardTagResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(CardTag cardTag, String boardId, String procedureId, String cardId, String userName) throws Exception {
        CardTagResource cardTagResource = new CardTagResource();
        cardTagResource.domainObject = cardTag;
        if (cardTag != null) {
            Link tagsLink = linkTo(methodOn(CardTagsController.class).stick(null, boardId, procedureId, cardId, null)).withRel("tags");
            cardTagResource.add(tlink.from(tagsLink).build(userName));

            Link cardLink = linkTo(methodOn(CardsController.class).findById(boardId, procedureId, cardId, userName)).withRel("card");
            cardTagResource.add(tlink.from(cardLink).build(userName));
        }
        return cardTagResource.getResource();
    }
}
