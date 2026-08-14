import type { Meta, StoryObj } from '@storybook/angular';
import { CreateAlertModal } from './create-alert-modal.component';

const meta: Meta<CreateAlertModal> = {
  title: 'Components/CreateAlertModal',
  component: CreateAlertModal,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<CreateAlertModal>;

export const Primary: Story = {
  args: {},
};
