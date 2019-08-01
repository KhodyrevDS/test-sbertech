package ru.kds.shoplist.service

import spock.lang.Specification

import java.time.LocalDate

/**
 * Specification for {@link SheduleItemTask}
 */
class SheduleItemTaskSpec extends Specification {

    private SheduleItemCreationService sheduleItemCreationService = Mock(SheduleItemCreationService)

    private SheduleItemTask sheduleItemTask = new SheduleItemTask(sheduleItemCreationService)

    def 'should create sheduled item'() {
        when:
        sheduleItemTask.createSheduledItem()

        then:
        1 * sheduleItemCreationService.createSheduledItem(_ as LocalDate)
    }
}
